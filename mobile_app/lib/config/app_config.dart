class AppConfig {
  // ✅ Émulateur Android → 10.0.2.2
  // ✅ Vrai appareil → IP de ton PC (ipconfig → Wi-Fi)
  // Ton IP WiFi actuelle est : 10.196.23.39

  static const String baseUrl = 'http://10.196.23.39:8081';
  // Si tu utilises la Gateway sur port 8888 :
  // static const String baseUrl = 'http://10.196.23.39:8888';

  static const String apiBaseUrl = '$baseUrl/api';
  static const String etudiants = '$apiBaseUrl/etudiants';
  static const String departements = '$apiBaseUrl/departements';
}