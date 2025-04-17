package fctreddit.impl.rest;

import java.util.List;
import java.util.logging.Logger;

import fctreddit.api.Post;
import fctreddit.api.java.Content;
import fctreddit.api.java.Users;
import fctreddit.api.rest.RestContent;
import fctreddit.impl.server.java.JavaContent;

public class ContentResource implements RestContent{
  private static Logger Log = Logger.getLogger(ImageResource.class.getName());

  final Content impl;

  public ContentResource(Users userServer) {
    impl = new JavaContent(userServer);
  }

  @Override
  public String createPost(Post post, String userPassword) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createPost'");
  }

  @Override
  public List<String> getPosts(long timestamp, String sortOrder) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPosts'");
  }

  @Override
  public Post getPost(String postId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPost'");
  }

  @Override
  public List<String> getPostAnswers(String postId, long timeout) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPostAnswers'");
  }

  @Override
  public Post updatePost(String postId, String userPassword, Post post) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updatePost'");
  }

  @Override
  public void deletePost(String postId, String userPassword) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deletePost'");
  }

  @Override
  public void upVotePost(String postId, String userId, String userPassword) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'upVotePost'");
  }

  @Override
  public void removeUpVotePost(String postId, String userId, String userPassword) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeUpVotePost'");
  }

  @Override
  public void downVotePost(String postId, String userId, String userPassword) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'downVotePost'");
  }

  @Override
  public void removeDownVotePost(String postId, String userId, String userPassword) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeDownVotePost'");
  }

  @Override
  public Integer getupVotes(String postId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getupVotes'");
  }

  @Override
  public Integer getDownVotes(String postId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getDownVotes'");
  }

 

}
