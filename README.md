# ZenWalls - Wallpaper App

ZenWalls is a sleek wallpaper app built using **Jetpack Compose** that offers a seamless browsing experience for high-quality wallpapers from **Unsplash**. The app allows users to search, explore, and download wallpapers in various resolutions with features like caching, offline access, and photographer profiles.

## 🚀 Features

- 🏠 **Home Screen** - Browse trending wallpapers effortlessly.  
- 🔍 **Search Functionality** - Find wallpapers by keywords.  
- ❤️ **Favorites Collection** - Save your favorite wallpapers for quick access.  
- 📸 **Photographer Profile Pages** - View photographer details via **WebView**.  
- ⬇️ **Download Wallpapers** - Available in multiple resolutions.  
- 🌐 **Active Network Monitoring** - Ensures smooth browsing even in varying network conditions.  
- 🗂️ **Image Caching with Paging 3** - Ensures optimal performance with seamless scrolling.  

## 🛠️ Tech Stack

- **Jetpack Compose** - Modern UI toolkit for building native UIs.  
- **Kotlin Coroutines + Ktor** - For efficient asynchronous network operations.  
- **Paging 3 with RemoteMediator** - Enables efficient data loading and caching.  
- **Room Database** - For local storage of downloaded wallpapers.  
- **Koin** - Lightweight dependency injection for improved modularity.  
- **ViewModel** - Manages UI-related data in a lifecycle-conscious way.  
- **Jetpack Navigation** - For smooth screen transitions.  

### 📸 Screenshots

| Home Screen | Search Screen | Favorites Screen |
|--------------|----------------|------------------|
| ![ss1](https://github.com/user-attachments/assets/042e97bd-3348-4f84-9d10-beb5195041e7) | ![ss6](https://github.com/user-attachments/assets/9363def7-7ae8-4faa-9cfa-586b60b0832a) | ![ss8](https://github.com/user-attachments/assets/96bca474-4fd6-40af-a49a-44e5f0ffd0df) |

| Photographer Profile | Wallpaper Zoom | Download Options |
|-----------------------|------------------|-------------------|
| ![ss5](https://github.com/user-attachments/assets/ab43ceb5-9215-4fcf-ba71-77ad0e090beb) | ![ss2](https://github.com/user-attachments/assets/8b7d2d57-7e5a-468e-a20d-d6bb1b86782c) | ![ss4](https://github.com/user-attachments/assets/457d1872-917f-4d72-ae14-50f1e658fdbf) |

### Key Fixes  
✅ Removed extra parentheses around image links.  
✅ Ensured each cell in the table has one image only.  
✅ Corrected syntax to follow GitHub Markdown standards.  

This version should display the images correctly. Let me know if you’d like to refine anything else! 🚀
## 🏗️ Setup Instructions

1. **Clone the Repository**  
```bash
git clone https://github.com/yourusername/ZenWalls.git
```

2. **Open the Project in Android Studio**  
3. **Add Unsplash API Key**  
   - Register on [Unsplash](https://unsplash.com/developers) and obtain your API key.  
   - Create a `local.properties` file in the root folder and add the following line:  
     ```
     UNSPLASH_ACCESS_KEY="your_api_key_here"
     UNSPLASH_SECRET_KEY="your_secret_key_here"
     ```
4. **Build and Run the App**

## 💡 How It Works

- **Unsplash API Integration:** Utilizes the Unsplash API for fetching wallpaper data.  
- **RemoteMediator with Paging 3:** Efficiently fetches data, caches it, and enables smooth scrolling.  
- **Room Database:** Caches downloaded images for offline access.  
- **WebView Integration:** Displays photographer profiles directly within the app.  
- **Active Network Monitor:** Ensures the app adapts to network changes seamlessly.  

## 🔥 Future Enhancements

- Dark mode support.  
- Improved download manager with batch downloads.  
- Enhanced UI animations for smoother transitions.  

## 🙌 Contributing

Contributions are welcome! If you'd like to improve the app or fix any issues, please create a pull request.


## ❤️ Acknowledgements
Special thanks to **Unsplash** for their amazing API and talented photographers for their stunning visuals.

