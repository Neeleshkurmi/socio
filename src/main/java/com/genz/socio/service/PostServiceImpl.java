package com.genz.socio.service;

import com.genz.socio.dto.entity.Comment;
import com.genz.socio.dto.entity.Post;
import com.genz.socio.dto.entity.Profile;
import com.genz.socio.dto.entity.User;
import com.genz.socio.dto.request.CommentRequest;
import com.genz.socio.dto.request.PostRequest;
import com.genz.socio.dto.response.EditPostRequest;
import com.genz.socio.dto.response.PostResponse;
import com.genz.socio.exception.ResourceNotFoundException;
import com.genz.socio.mapper.PostMapper;
import com.genz.socio.repo.PostRepository;
import com.genz.socio.repo.ProfileRepository;
import com.genz.socio.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final PostMapper postMapper;
    private final ProfileRepository profileRepository;



    @Transactional
    @Override
    public PostResponse createPost(PostRequest request, User user) {
        Post post = new Post(request.getUrl(), request.getTitle(),0L
                            , user, new ArrayList<>(), new HashSet<>());

        postRepository.save(post);

        return postMapper.toResponse(post);
    }

    @Override
    public String deletePost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(()->
                                    new ResourceNotFoundException("post not found"));

        User user1 = userRepository.findByUserName(user.getUserName()).orElseThrow();

        user1.getPosts().remove(post);

        postRepository.delete(post);
        userRepository.save(user1);

        return "post deleted successfully";
    }

    @Override
    public PostResponse editPost(EditPostRequest request, User user) {
        Post post = postRepository.findById(request.id()).orElseThrow(()->
                            new ResourceNotFoundException("post not found"));

        post.setTitle(request.title());
        Post response = postRepository.save(post);

        return postMapper.toResponse(response);
    }

    @Override
    public PostResponse likeOrDislikePost(User likedBy, Post post) {

        if(post.getLikedBy().contains(likedBy.getProfile())){
            post.getLikedBy().remove(likedBy.getProfile());
        }
        else{
            post.getLikedBy().add(likedBy.getProfile());
        }

        postRepository.save(post);
        return postMapper.toResponse(post);
    }

    @Override
    public PostResponse commentOnPost(User commentBy, Post post, CommentRequest comment) {
        post.addComment(new Comment(comment.getText(),post,commentBy));
        postRepository.save(post);

        return postMapper.toResponse(post);
    }

    @Override
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post not found"));

        return postMapper.toResponse(post);
    }

    @Override
    public List<PostResponse> getAllPosts(User user) {
        List<Post> posts= user.getPosts();

        List<PostResponse> postResponses = new ArrayList<>();

        for(Post post: posts){
            postResponses.add(postMapper.toResponse(post));
        }

        return postResponses;
    }

    @Override
    public PostResponse sharePost(User user, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post not found"));

        post.setShares(post.getShares()+1);

        postRepository.save(post);

        return postMapper.toResponse(post);
    }

    public PostResponse getLatestPosts(User user, LocalDateTime dateTime){

        List<Post> posts = user.getPosts();

        for(Post post: posts){
            if(post.getCreatedAt().isBefore(dateTime) && post.getCreatedAt().isAfter(dateTime.minusDays(4))){
                return postMapper.toResponse(post);
            }
        }
        return postMapper.toResponse(posts.getLast());
    }
}
