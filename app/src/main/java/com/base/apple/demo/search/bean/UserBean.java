package com.base.apple.demo.search.bean;


import com.apple.bean.BaseBean;

import java.util.List;


/**
 * https://api.github.com/users/kjhealy
 * 解析对象
 */
public class UserBean extends BaseBean {


    /**
     * total_count : 182
     * incomplete_results : false
     * items : [{"login":"kjhealy","id":5760,"avatar_url":"https://avatars1.githubusercontent.com/u/5760?v=3","gravatar_id":"","url":"https://api.github.com/users/kjhealy","html_url":"https://github.com/kjhealy","followers_url":"https://api.github.com/users/kjhealy/followers","following_url":"https://api.github.com/users/kjhealy/following{/other_user}","gists_url":"https://api.github.com/users/kjhealy/gists{/gist_id}","starred_url":"https://api.github.com/users/kjhealy/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/kjhealy/subscriptions","organizations_url":"https://api.github.com/users/kjhealy/orgs","repos_url":"https://api.github.com/users/kjhealy/repos","events_url":"https://api.github.com/users/kjhealy/events{/privacy}","received_events_url":"https://api.github.com/users/kjhealy/received_events","type":"User","site_admin":false,"score":34.434128},{"login":"yob","id":8132,"avatar_url":"https://avatars1.githubusercontent.com/u/8132?v=3","gravatar_id":"","url":"https://api.github.com/users/yob","html_url":"https://github.com/yob","followers_url":"https://api.github.com/users/yob/followers","following_url":"https://api.github.com/users/yob/following{/other_user}","gists_url":"https://api.github.com/users/yob/gists{/gist_id}","starred_url":"https://api.github.com/users/yob/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/yob/subscriptions","organizations_url":"https://api.github.com/users/yob/orgs","repos_url":"https://api.github.com/users/yob/repos","events_url":"https://api.github.com/users/yob/events{/privacy}","received_events_url":"https://api.github.com/users/yob/received_events","type":"User","site_admin":false,"score":31.149206},{"login":"healyke","id":6390710,"avatar_url":"https://avatars0.githubusercontent.com/u/6390710?v=3","gravatar_id":"","url":"https://api.github.com/users/healyke","html_url":"https://github.com/healyke","followers_url":"https://api.github.com/users/healyke/followers","following_url":"https://api.github.com/users/healyke/following{/other_user}","gists_url":"https://api.github.com/users/healyke/gists{/gist_id}","starred_url":"https://api.github.com/users/healyke/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/healyke/subscriptions","organizations_url":"https://api.github.com/users/healyke/orgs","repos_url":"https://api.github.com/users/healyke/repos","events_url":"https://api.github.com/users/healyke/events{/privacy}","received_events_url":"https://api.github.com/users/healyke/received_events","type":"User","site_admin":false,"score":23.834356}]
     */

    private int total_count;
    private boolean incomplete_results;
    private List<ItemsBean> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * login : kjhealy
         * id : 5760
         * avatar_url : https://avatars1.githubusercontent.com/u/5760?v=3
         * gravatar_id :
         * url : https://api.github.com/users/kjhealy
         * html_url : https://github.com/kjhealy
         * followers_url : https://api.github.com/users/kjhealy/followers
         * following_url : https://api.github.com/users/kjhealy/following{/other_user}
         * gists_url : https://api.github.com/users/kjhealy/gists{/gist_id}
         * starred_url : https://api.github.com/users/kjhealy/starred{/owner}{/repo}
         * subscriptions_url : https://api.github.com/users/kjhealy/subscriptions
         * organizations_url : https://api.github.com/users/kjhealy/orgs
         * repos_url : https://api.github.com/users/kjhealy/repos
         * events_url : https://api.github.com/users/kjhealy/events{/privacy}
         * received_events_url : https://api.github.com/users/kjhealy/received_events
         * type : User
         * site_admin : false
         * score : 34.434128
         */

        private String login;
        private int id;
        private String avatar_url;
        private String gravatar_id;
        private String url;
        private String html_url;
        private String followers_url;
        private String following_url;
        private String gists_url;
        private String starred_url;
        private String subscriptions_url;
        private String organizations_url;
        private String repos_url;
        private String events_url;
        private String received_events_url;
        private String type;
        private boolean site_admin;
        private double score;

        private String language="";


        public String getLanguage() {
            return language==null?"":language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getGravatar_id() {
            return gravatar_id;
        }

        public void setGravatar_id(String gravatar_id) {
            this.gravatar_id = gravatar_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public String getFollowers_url() {
            return followers_url;
        }

        public void setFollowers_url(String followers_url) {
            this.followers_url = followers_url;
        }

        public String getFollowing_url() {
            return following_url;
        }

        public void setFollowing_url(String following_url) {
            this.following_url = following_url;
        }

        public String getGists_url() {
            return gists_url;
        }

        public void setGists_url(String gists_url) {
            this.gists_url = gists_url;
        }

        public String getStarred_url() {
            return starred_url;
        }

        public void setStarred_url(String starred_url) {
            this.starred_url = starred_url;
        }

        public String getSubscriptions_url() {
            return subscriptions_url;
        }

        public void setSubscriptions_url(String subscriptions_url) {
            this.subscriptions_url = subscriptions_url;
        }

        public String getOrganizations_url() {
            return organizations_url;
        }

        public void setOrganizations_url(String organizations_url) {
            this.organizations_url = organizations_url;
        }

        public String getRepos_url() {
            return repos_url;
        }

        public void setRepos_url(String repos_url) {
            this.repos_url = repos_url;
        }

        public String getEvents_url() {
            return events_url;
        }

        public void setEvents_url(String events_url) {
            this.events_url = events_url;
        }

        public String getReceived_events_url() {
            return received_events_url;
        }

        public void setReceived_events_url(String received_events_url) {
            this.received_events_url = received_events_url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isSite_admin() {
            return site_admin;
        }

        public void setSite_admin(boolean site_admin) {
            this.site_admin = site_admin;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}
