package fctreddit.api;

/**
 * Represents a Post and a Reply in the system
 */

public class Post {

	private String postId;
	private String authorId;
	private long creationTimestamp;
	private String content;
	private String mediaUrl;
	private String parentUrl; //This should be null when this is a top level post.
	private int upVote;
	private int downVote;
	
	
	public Post() {
		
	}
	
	public Post(String authorId, String content) {
		this.postId = null;
		this.authorId = authorId;
		this.creationTimestamp = System.currentTimeMillis();
		this.content = content;
		this.mediaUrl = null;
		this.parentUrl = null;
		this.upVote = 0;
		this.downVote = 0;
	}
	
	public Post(String authorId, String content, String parentUrl) {
		this(authorId, content);
		this.parentUrl = parentUrl;
	}
	
	public Post(String authorId, String content, String parentUrl, String mediaUrl) {
		this(authorId, content, parentUrl);
		this.mediaUrl = mediaUrl;
	}
	
	public Post(String postId, String authorId, long creationTime, String content, String mediaUrl, String parentUrl, int upVote, int downVote) {
		this.postId = postId;
		this.authorId = authorId;
		this.creationTimestamp = creationTime;
		this.content = content;
		this.mediaUrl = mediaUrl;
		this.parentUrl = parentUrl;
		this.upVote = upVote;
		this.downVote = downVote;
	}
	
	
	
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public long getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getParentUrl() {
		return parentUrl;
	}

	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}

	public int getUpVote() {
		return upVote;
	}

	public void setUpVote(int upVote) {
		this.upVote = upVote;
	}

	public int getDownVote() {
		return downVote;
	}

	public void setDownVote(int downVote) {
		this.downVote = downVote;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((postId == null) ? 0 : postId.hashCode());
		result = prime * result + ((authorId == null) ? 0 : authorId.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((parentUrl == null) ? 0 : parentUrl.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "Post [postId=" + postId + ", authorId=" + authorId + ", content=" + content + ", mediaUrl=" + mediaUrl
				+ ", parentUrl=" + parentUrl + ", creationTimestamp=" + creationTimestamp + ", upVote=" + upVote + ", downVote=" + downVote + "]";
	}
}
