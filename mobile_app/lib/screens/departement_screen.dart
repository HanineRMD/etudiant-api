import 'package:flutter/material.dart';
import '../models/departement.dart';
import '../models/etudiant.dart';
import '../services/api_service.dart';

class DepartementScreen extends StatefulWidget {
  const DepartementScreen({super.key});

  @override
  State<DepartementScreen> createState() => _DepartementScreenState();
}

class _DepartementScreenState extends State<DepartementScreen> {
  final ApiService _api = ApiService();
  List<Departement> _departements = [];
  List<Etudiant> _etudiants = [];
  Departement? _selected;
  bool _loading = true;
  bool _loadingEtudiants = false;
  String? _error;

  @override
  void initState() {
    super.initState();
    _loadDepartements();
  }

  Future<void> _loadDepartements() async {
    setState(() { _loading = true; _error = null; });
    try {
      final depts = await _api.getDepartements();
      setState(() { _departements = depts; _loading = false; });
    } catch (e) {
      setState(() {
        _error = 'Impossible de charger les departements: $e';
        _loading = false;
      });
    }
  }

  Future<void> _loadEtudiants(Departement dept) async {
    setState(() { _loadingEtudiants = true; _selected = dept; _etudiants = []; });
    try {
      final etudiants = await _api.getEtudiantsByDepartement(dept.id);
      setState(() { _etudiants = etudiants; _loadingEtudiants = false; });
    } catch (e) {
      setState(() { _loadingEtudiants = false; });
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Erreur: $e'), backgroundColor: Colors.red),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF0A0F1E),
      appBar: AppBar(
        backgroundColor: const Color(0xFF111827),
        elevation: 0,
        title: const Row(
          children: [
            Text('🎓', style: TextStyle(fontSize: 20)),
            SizedBox(width: 8),
            Text(
              'EduManager',
              style: TextStyle(
                color: Colors.white,
                fontWeight: FontWeight.w700,
                fontSize: 18,
                letterSpacing: -0.5,
              ),
            ),
          ],
        ),
        actions: [
          IconButton(
            onPressed: _loadDepartements,
            icon: const Icon(Icons.refresh, color: Colors.white70),
          ),
        ],
        bottom: PreferredSize(
          preferredSize: const Size.fromHeight(1),
          child: Container(color: Colors.white12, height: 1),
        ),
      ),
      body: _loading
          ? const Center(child: CircularProgressIndicator(color: Color(0xFF6366F1)))
          : _error != null
          ? _buildError()
          : Column(
        children: [
          _buildHeader(),
          _buildDepartementSelector(),
          if (_selected != null) _buildEtudiantsList(),
        ],
      ),
    );
  }

  Widget _buildError() {
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Container(
              width: 64, height: 64,
              decoration: BoxDecoration(
                color: Colors.red.withOpacity(0.1),
                shape: BoxShape.circle,
                border: Border.all(color: Colors.red.withOpacity(0.3)),
              ),
              child: const Icon(Icons.error_outline, color: Colors.redAccent, size: 32),
            ),
            const SizedBox(height: 16),
            Text(
              _error!,
              textAlign: TextAlign.center,
              style: const TextStyle(color: Colors.redAccent, fontSize: 13),
            ),
            const SizedBox(height: 20),
            ElevatedButton.icon(
              onPressed: _loadDepartements,
              icon: const Icon(Icons.refresh),
              label: const Text('Reessayer'),
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color(0xFF6366F1),
                foregroundColor: Colors.white,
                padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildHeader() {
    return Container(
      padding: const EdgeInsets.all(20),
      child: Row(
        children: [
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text(
                  'Etudiants par Departement',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 20,
                    fontWeight: FontWeight.w700,
                    letterSpacing: -0.5,
                  ),
                ),
                const SizedBox(height: 4),
                Text(
                  '${_departements.length} departements disponibles',
                  style: TextStyle(color: Colors.white.withOpacity(0.5), fontSize: 13),
                ),
              ],
            ),
          ),
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
            decoration: BoxDecoration(
              color: const Color(0xFF6366F1).withOpacity(0.15),
              borderRadius: BorderRadius.circular(20),
              border: Border.all(color: const Color(0xFF6366F1).withOpacity(0.3)),
            ),
            child: Text(
              'API: 8081',
              style: TextStyle(
                color: const Color(0xFF6366F1).withOpacity(0.9),
                fontSize: 11,
                fontFamily: 'monospace',
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildDepartementSelector() {
    return SizedBox(
      height: 52,
      child: ListView.builder(
        scrollDirection: Axis.horizontal,
        padding: const EdgeInsets.symmetric(horizontal: 20),
        itemCount: _departements.length,
        itemBuilder: (ctx, i) {
          final dept = _departements[i];
          final selected = _selected?.id == dept.id;
          return GestureDetector(
            onTap: () => _loadEtudiants(dept),
            child: AnimatedContainer(
              duration: const Duration(milliseconds: 200),
              margin: const EdgeInsets.only(right: 8),
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
              decoration: BoxDecoration(
                gradient: selected
                    ? const LinearGradient(colors: [Color(0xFF6366F1), Color(0xFF8B5CF6)])
                    : null,
                color: selected ? null : const Color(0xFF1A2235),
                borderRadius: BorderRadius.circular(10),
                border: Border.all(
                  color: selected
                      ? Colors.transparent
                      : Colors.white.withOpacity(0.1),
                ),
                boxShadow: selected
                    ? [BoxShadow(color: const Color(0xFF6366F1).withOpacity(0.3), blurRadius: 12)]
                    : null,
              ),
              child: Row(
                children: [
                  const Text('🏛️', style: TextStyle(fontSize: 14)),
                  const SizedBox(width: 6),
                  Text(
                    dept.nom,
                    style: TextStyle(
                      color: selected ? Colors.white : Colors.white70,
                      fontWeight: selected ? FontWeight.w600 : FontWeight.w400,
                      fontSize: 13,
                    ),
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }

  Widget _buildEtudiantsList() {
    return Expanded(
      child: _loadingEtudiants
          ? const Center(child: CircularProgressIndicator(color: Color(0xFF6366F1)))
          : _etudiants.isEmpty
          ? Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text('👤', style: TextStyle(fontSize: 40)),
            const SizedBox(height: 12),
            Text(
              'Aucun etudiant dans ${_selected?.nom}',
              style: const TextStyle(color: Colors.white54, fontSize: 14),
            ),
          ],
        ),
      )
          : ListView.builder(
        padding: const EdgeInsets.all(16),
        itemCount: _etudiants.length,
        itemBuilder: (ctx, i) => _buildEtudiantCard(_etudiants[i], i),
      ),
    );
  }

  Widget _buildEtudiantCard(Etudiant e, int index) {
    final colors = [
      const Color(0xFF6366F1),
      const Color(0xFF8B5CF6),
      const Color(0xFF10B981),
      const Color(0xFFF59E0B),
    ];
    final color = colors[index % colors.length];

    return Container(
      margin: const EdgeInsets.only(bottom: 10),
      decoration: BoxDecoration(
        color: const Color(0xFF111827),
        borderRadius: BorderRadius.circular(14),
        border: Border.all(color: Colors.white.withOpacity(0.07)),
      ),
      child: ListTile(
        contentPadding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
        leading: Container(
          width: 44, height: 44,
          decoration: BoxDecoration(
            gradient: LinearGradient(
              colors: [color, color.withOpacity(0.6)],
              begin: Alignment.topLeft, end: Alignment.bottomRight,
            ),
            borderRadius: BorderRadius.circular(12),
          ),
          child: Center(
            child: Text(
              e.nom.isNotEmpty ? e.nom[0].toUpperCase() : '?',
              style: const TextStyle(
                color: Colors.white, fontWeight: FontWeight.w700, fontSize: 16,
              ),
            ),
          ),
        ),
        title: Text(
          e.nom,
          style: const TextStyle(
            color: Colors.white, fontWeight: FontWeight.w600, fontSize: 14,
          ),
        ),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const SizedBox(height: 4),
            Row(
              children: [
                Container(
                  padding: const EdgeInsets.symmetric(horizontal: 7, vertical: 2),
                  decoration: BoxDecoration(
                    color: color.withOpacity(0.15),
                    borderRadius: BorderRadius.circular(6),
                    border: Border.all(color: color.withOpacity(0.3)),
                  ),
                  child: Text(
                    e.cin,
                    style: TextStyle(
                      color: color.withOpacity(0.9),
                      fontSize: 11,
                      fontFamily: 'monospace',
                    ),
                  ),
                ),
                const SizedBox(width: 8),
                if (e.dateNaissance.isNotEmpty)
                  Text(
                    e.dateNaissance,
                    style: const TextStyle(color: Colors.white38, fontSize: 11),
                  ),
              ],
            ),
            if (e.email != null && e.email!.isNotEmpty) ...[
              const SizedBox(height: 3),
              Text(
                e.email!,
                style: const TextStyle(color: Colors.white38, fontSize: 11),
              ),
            ],
          ],
        ),
        trailing: Text(
          e.anneePremiereInscription.toString(),
          style: TextStyle(
            color: Colors.white.withOpacity(0.3),
            fontSize: 12,
            fontFamily: 'monospace',
          ),
        ),
      ),
    );
  }
}