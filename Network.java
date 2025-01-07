/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {

        for (int i = 0; i < users.length; i ++) {
            if (users[i] != null && users[i].getName().equals(name) ) {
                return users[i];
            }
        }

        return null;
    }

    public int getUserCount() {
        return userCount;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {

        if (users.length == userCount) {
            System.out.println("Network is full! cannot add " + name);
            return false;
        }

        if (getUser(name) != null) {
            System.out.println(name + " is already part of the Network!");
            return false;
        }

        users[userCount] = new User(name);
        userCount++;
        System.out.println("Successfully added " + name + " to the Network!");

        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {

        if (getUser(name1) == null || getUser(name2) == null) {
            System.out.println("One of the users is not part of the Network!");
            return false;
        }

        if (!getUser(name1).addFollowee(name2)) {
            System.out.println("There was an issue adding " + name1 + " to the following list of " + name2);
            return false;
        }

        return true;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {

        User mostRecommendedUserToFollow = null;
        int currentRecommendedCount = 0;

        for (int i = 0; i < userCount; i++) {
            if (users[i].countMutual(getUser(name)) >= currentRecommendedCount && !users[i].getName().equals(name)) {           // we iterate as long as i < userCount, so no need for null check
                mostRecommendedUserToFollow = users[i];
                currentRecommendedCount = users[i].countMutual(getUser(name));
            }
        }

        return mostRecommendedUserToFollow.getName();           // always returns a user, so no need for null check
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        
        User mostPopUser = null;
        int currentPopUserCount = 0;

        for (int i = 0; i < userCount; i++){
            if (followeeCount(users[i].getName()) > currentPopUserCount) {
                mostPopUser = users[i];
                currentPopUserCount = followeeCount(users[i].getName());
            }
        }

        return mostPopUser.getName();
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int followersCount = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].follows(name)) {
                followersCount++;
            }
        }
        return followersCount;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String ans = "";
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                return ans;
            }
            ans += users[i].toString() + "\n";
        }
        return ans;
    }
}
