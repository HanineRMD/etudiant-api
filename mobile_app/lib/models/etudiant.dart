class Etudiant {
  final int id;
  final String cin;
  final String nom;
  final String dateNaissance;
  final String? email;
  final int anneePremiereInscription;
  final int? departementId;
  final String? departementNom;

  Etudiant({
    required this.id,
    required this.cin,
    required this.nom,
    required this.dateNaissance,
    this.email,
    required this.anneePremiereInscription,
    this.departementId,
    this.departementNom,
  });

  factory Etudiant.fromJson(Map<String, dynamic> json) {
    return Etudiant(
      id: json['id'] ?? 0,
      cin: json['cin'] ?? '',
      nom: json['nom'] ?? '',
      dateNaissance: json['dateNaissance'] ?? '',
      email: json['email'],
      anneePremiereInscription: json['anneePremiereInscription'] ?? 0,
      departementId: json['departementId'],
      departementNom: json['departementNom'],
    );
  }
}