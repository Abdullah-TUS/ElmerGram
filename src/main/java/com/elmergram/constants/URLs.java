package com.elmergram.constants;

public class URLs {
    public static class USER {
        public static final String BASE_URL = "/api/v1/users";
        public static final String GET_USERS = "";
        public static final String GET_USER = "/{username}";
        public static final String GET_USER_POSTS = "/{username}/posts";
        public static final String GET_USER_POST_DETAILS = "/{username}/posts/{postId}";

    }
    public static class POST {
        public static final String BASE_URL = "/api/v1/posts";
        public static final String GET_POST="{postId}";
    }
}
