# Frontend Project - Samuel Deotti

This project aims to develop a front-end application for a vinyl record e-commerce platform. Users can view, search, and buy albums, add them to their collection, view their album collection, and remove albums from the collection.

## How to Build and Run the Application

1. Clone the project from GitHub:
    ```bash
    git clone https://github.com/bc-fullstack-04/samuel-deotti-frontend.git
    ```
   or
    ```bash
    git clone git@github.com:bc-fullstack-04/samuel-deotti-frontend.git
    ```

2. Navigate to the project directory:
    ```bash
    cd samuel-deotti-frontend
    ```

3. Start the Docker Compose services:
    ```bash
    docker compose up
    ```
4. In another terminal tab run:
    ```bash
    npm install
    ```

5. Start the development server:
    ```bash
    npm run dev
    ```

6. Wait for a new tab to open in your browser, or open it manually. The application usually runs on port 5173, but it may open on a different port. If you encounter issues, check the terminal for the specified port.
    ```bash
    http://localhost:5173/
    ```

## How to Stop the Application

To stop the application, press `Ctrl + C` in the terminal where Docker Compose and the development server are running.

## Routes

- /: `Landing page`
- /login: `User login page`
- /signup: `User signup page`
- /dashboard: `Dashboard for viewing, searching, and buying albums`
- /albums/my-collection: `Page to view and manage the user's album collection`
