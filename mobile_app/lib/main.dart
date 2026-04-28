import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

const String baseUrl = 'http://10.0.2.2:8080';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  @override
  Widget build(BuildContext context) => MaterialApp(
    title: 'Etudiants',
    theme: ThemeData(primarySwatch: Colors.blue, useMaterial3: true),
    home: const DepartementScreen(),
  );
}

class DepartementScreen extends StatefulWidget {
  const DepartementScreen({super.key});
  @override
  State<DepartementScreen> createState() => _DepartementScreenState();
}

class _DepartementScreenState extends State<DepartementScreen> {
  List departements = [];
  List etudiants = [];
  Map? selectedDept;
  bool loading = true;

  @override
  void initState() {
    super.initState();
    _loadDepartements();
  }

  Future<void> _loadDepartements() async {
    final res = await http.get(Uri.parse('$baseUrl/api/departements'));
    if (res.statusCode == 200) {
      setState(() {
        departements = json.decode(res.body);
        loading = false;
      });
    }
  }

  Future<void> _loadEtudiants(int deptId) async {
    setState(() => loading = true);
    final res = await http.get(Uri.parse('$baseUrl/api/etudiants'));
    if (res.statusCode == 200) {
      final all = json.decode(res.body) as List;
      setState(() {
        etudiants = all.where((e) => e['departementId'] == deptId).toList();
        loading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Etudiants par Departement'),
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
      ),
      body: loading
          ? const Center(child: CircularProgressIndicator())
          : Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(16),
            child: DropdownButtonFormField(
              decoration: const InputDecoration(
                labelText: 'Choisir un departement',
                border: OutlineInputBorder(),
              ),
              items: departements.map((d) {
                return DropdownMenuItem(
                    value: d, child: Text(d['nom']));
              }).toList(),
              onChanged: (val) {
                setState(() => selectedDept = val as Map);
                _loadEtudiants((val as Map)['id']);
              },
            ),
          ),
          Expanded(
            child: etudiants.isEmpty
                ? const Center(child: Text('Selectionnez un departement'))
                : ListView.builder(
              itemCount: etudiants.length,
              itemBuilder: (ctx, i) {
                final e = etudiants[i];
                return Card(
                  margin: const EdgeInsets.symmetric(
                      horizontal: 16, vertical: 4),
                  child: ListTile(
                    leading: CircleAvatar(
                      backgroundColor: Colors.blue,
                      child: Text(
                        e['nom'][0].toUpperCase(),
                        style: const TextStyle(color: Colors.white),
                      ),
                    ),
                    title: Text(e['nom'],
                        style: const TextStyle(
                            fontWeight: FontWeight.bold)),
                    subtitle: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text('CIN: ${e['cin']}'),
                        Text('Naissance: ${e['dateNaissance']}'),
                        Text('Email: ${e['email'] ?? '-'}'),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}