# Cocktail Explorer 🍸

A modern Android app built with Jetpack Compose that helps users discover cocktails using TheCocktailDB API. Features Firebase authentication, dynamic search/filtering, and alphabetical navigation.

---

## Features ✨

### Core Features
- **Firebase Authentication**: Secure email/password login and registration
- **Dynamic Cocktail Browser**:
  - Default view shows cocktails starting with letter 'A'
  - Lazy horizontal letter row for alphabetical navigation
  - Search cocktails by name
  - Filter cocktails by category (spirit type)
- **Modern UI**:
  - Built with Jetpack Compose
  - Responsive design for all screen sizes
  - Loading states and error handling
- **Caching & Optimization**:
  - Image loading with Coil
  - Pagination for large result sets
- **Clean Architecture**:
  - MVVM pattern with repository layer
  - Hilt for dependency injection

---

## ScreenShots

<img width="328" alt="Screenshot 8 43 37 PM" src="https://github.com/user-attachments/assets/be48c7ba-19bb-4913-aee1-eac4ec127278" />
<img width="327" alt="Screenshot 8 43 06 PM" src="https://github.com/user-attachments/assets/bea3955a-ed96-4f27-97a9-09169d0f502a" />
<img width="330" alt="Screenshot 8 43 19 PM" src="https://github.com/user-attachments/assets/f183472b-28ef-4926-b6ad-00dccc3b030d" />




## Tech Stack 🛠️

- **UI**: Jetpack Compose
- **Authentication**: Firebase Auth
- **Networking**: Retrofit + Moshi
- **DI**: Hilt
- **Async**: Coroutines + Flow
- **Image Loading**: Coil
- **Navigation**: Compose Navigation
- **Testing**: JUnit, Truth, MockWebServer

---

## Installation 🚀

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/cocktail-explorer.git
