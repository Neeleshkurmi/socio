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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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


    private final String cacheKey = "#user.userName";
    private final String cacheName = "postdata";


    @Transactional
    @Override
    @Cacheable(cacheNames = cacheName, key = cacheKey)
    public PostResponse createPost(PostRequest request, User user) {
        Post post = new Post(request.getUrl(), request.getTitle(),0L
                            , user, new ArrayList<>(), new HashSet<>());

        postRepository.save(post);

        return postMapper.toResponse(post);
    }

    @Override
    @CacheEvict(cacheNames = cacheName, key = cacheKey)
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
    @CachePut(cacheNames = cacheName, key = cacheKey)
    public PostResponse editPost(EditPostRequest request, User user) {
        Post post = postRepository.findById(request.id()).orElseThrow(()->
                            new ResourceNotFoundException("post not found"));

        post.setTitle(request.title());
        Post response = postRepository.save(post);

        return postMapper.toResponse(response);
    }

    @Override
    @CachePut(cacheNames = cacheName, key = cacheKey)
    public PostResponse likeOrDislikePost(User user, Post post) {

        if(post.getLikedBy().contains(user.getProfile())){
            post.getLikedBy().remove(user.getProfile());
        }
        else{
            post.getLikedBy().add(user.getProfile());
        }

        postRepository.save(post);
        return postMapper.toResponse(post);
    }

    @Override
    @CachePut(cacheNames = cacheName, key = cacheKey)
    public PostResponse commentOnPost(User user, Post post, CommentRequest comment) {
        post.addComment(new Comment(comment.getText(),post,user));
        postRepository.save(post);

        return postMapper.toResponse(post);
    }

    @Override
    @Cacheable(cacheNames = cacheName, key = cacheKey)
    public PostResponse getPost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post not found"));

        return postMapper.toResponse(post);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "allposts", key = cacheKey)
    public List<PostResponse> getAllPosts(User user) {
        List<Post> posts= user.getPosts();

        List<PostResponse> postResponses = new ArrayList<>();

        for(Post post: posts){
            postResponses.add(postMapper.toResponse(post));
        }

        return postResponses;
    }

    @Override
    @CachePut(cacheNames = cacheName, key = cacheKey)
    public PostResponse sharePost(User user, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post not found"));

        post.setShares(post.getShares()+1);

        postRepository.save(post);

        return postMapper.toResponse(post);
    }

    @Cacheable(cacheNames = cacheName, key = cacheKey)
    public PostResponse getLatestPosts(User user, LocalDateTime dateTime) {
        List<Post> posts = user.getPosts();
        if (posts == null || posts.isEmpty()) {
            throw new ResourceNotFoundException("No posts found");
        }

        return posts.stream()
                .filter(post -> post.getCreatedAt().isBefore(dateTime) &&
                        post.getCreatedAt().isAfter(dateTime.minusDays(4)))
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .findFirst()
                .map(postMapper::toResponse)
                .orElseGet(() -> postMapper.toResponse(posts.get(posts.size() - 1)));
    }
}
