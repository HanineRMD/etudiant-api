import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/etudiant.dart';
import '../config/app_config.dart';

class ApiService {
  Future<List<Etudiant>> getEtudiants() async {
    try {
      print('Connexion à: ${AppConfig.etudiants}');
      final response = await http.get(Uri.parse(AppConfig.etudiants));

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

  Future<List<Map<String, dynamic>>> getDepartements() async {
    try {
      final response = await http.get(Uri.parse(AppConfig.departements));
      if (response.statusCode == 200) {
        return List<Map<String, dynamic>>.from(json.decode(response.body));
      } else {
        throw Exception('Échec du chargement des départements');
      }
    } catch (e) {
      print('Erreur départements: $e');
      throw Exception('Erreur de connexion pour départements');
    }
  }
}