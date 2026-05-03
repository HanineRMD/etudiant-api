import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/etudiant.dart';
import '../models/departement.dart';
import '../config/app_config.dart';

class ApiService {
  static const Duration timeout = Duration(seconds: 15);

  Future<List<Departement>> getDepartements() async {
    try {
      print('Connexion departements: ${AppConfig.departements}');
      final response = await http
          .get(Uri.parse(AppConfig.departements))
          .timeout(timeout, onTimeout: () {
        throw Exception('Timeout de connexion - verifie que Docker tourne');
      });

      print('Status: ${response.statusCode}');

      if (response.statusCode == 200) {
        final List data = json.decode(response.body);
        return data.map((d) => Departement.fromJson(d)).toList();
      } else {
        throw Exception('Erreur ${response.statusCode}');
      }
    } catch (e) {
      print('Erreur getDepartements: $e');
      rethrow;
    }
  }

  Future<List<Etudiant>> getEtudiants() async {
    try {
      print('Connexion etudiants: ${AppConfig.etudiants}');
      final response = await http
          .get(Uri.parse(AppConfig.etudiants))
          .timeout(timeout, onTimeout: () {
        throw Exception('Timeout de connexion');
      });

      if (response.statusCode == 200) {
        final List data = json.decode(response.body);
        return data.map((e) => Etudiant.fromJson(e)).toList();
      } else {
        throw Exception('Erreur ${response.statusCode}');
      }
    } catch (e) {
      print('Erreur getEtudiants: $e');
      rethrow;
    }
  }

  Future<List<Etudiant>> getEtudiantsByDepartement(int deptId) async {
    final List<Etudiant> tous = await getEtudiants();
    return tous.where((e) => e.departementId == deptId).toList();
  }
}