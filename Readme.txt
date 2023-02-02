I. Functionalities, Technology
    a. Functionalities
        - Display products, cart items, order history, user's profile.
        - Create & update user, cart items.
        - Search for specific product.
        - Filter product by category.
        - Login & Register.
        - Forgot password.
    b. Technology
        - Android Studio.
        - Programming language: Java.
        - Firebase (Authentication, Realtime Database, Firestore Database).

II. Issues
    - Search view shifted the bottom layout down.
    - Slow loading images.
    - Bad backstack (Have fixed it).
    - Search function does not return proper results (line 180-HomeFragment):
        "The if statement returns true when it found the containing character. However, the result return the statement in the else condition."