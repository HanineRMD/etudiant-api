class AppConfig {
  // Pour l'émulateur Android : 10.0.2.2
  // Pour le vrai appareil : l'IP de ton ordinateur
  static const String baseUrl = 'http://10.0.2.2:8888';

  static const String apiBaseUrl = '$baseUrl/api';

  // Endpoints
  static const String etudiants = '$apiBaseUrl/etudiants';
  static const String departements = '$apiBaseUrl/departements';
}