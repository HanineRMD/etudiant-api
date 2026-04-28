import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/etudiant.dart';

class ApiService {
  static const String baseUrl = 'http://localhost:8081/api/etudiants';
  Future<List<Etudiant>> getEtudiants() async {
    try {
      print('Connexion à: $baseUrl');
      final response = await http.get(Uri.parse(baseUrl));

      print('Status code: ${response.statusCode}');

      if (response.statusCode == 200) {
        List jsonResponse = json.decode(response.body);
        print('Nombre d\'étudiants: ${jsonResponse.length}');
        return jsonResponse.map((data) => Etudiant.fromJson(data)).toList();
      } else {
        throw Exception('Échec du chargement: ${response.statusCode}');
      }
    } catch (e) {
      print('Erreur: $e');
      throw Exception('Erreur de connexion: $e');
    }
  }
}