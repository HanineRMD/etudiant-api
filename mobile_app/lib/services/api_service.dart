import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/etudiant.dart';

class ApiService {
  // Pour l'émulateur Android, utilisez 10.0.2.2
  // Pour un vrai appareil, utilisez l'IP de votre machine
  static const String baseUrl = 'http://10.0.2.2:8080/api/etudiants';

  Future<List<Etudiant>> getEtudiants() async {
    try {
      final response = await http.get(Uri.parse(baseUrl));

      if (response.statusCode == 200) {
        List jsonResponse = json.decode(response.body);
        return jsonResponse.map((data) => Etudiant.fromJson(data)).toList();
      } else {
        throw Exception('Échec du chargement des étudiants');
      }
    } catch (e) {
      throw Exception('Erreur de connexion: $e');
    }
  }
}