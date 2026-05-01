"use client";
import { useEffect, useState } from "react";
import axios from "axios";

const API = "http://localhost:8081";

export default function EtudiantsPage() {
    const [etudiants, setEtudiants] = useState<any[]>([]);
    const [departements, setDepartements] = useState<any[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [showForm, setShowForm] = useState(false);
    const [editId, setEditId] = useState<number | null>(null);
    const [saving, setSaving] = useState(false);
    const [form, setForm] = useState({
        cin: "", nom: "", dateNaissance: "",
        email: "", anneePremiereInscription: "", departementId: ""
    });

    const load = () => {
        setLoading(true);
        Promise.all([
            axios.get(`${API}/api/etudiants`),
            axios.get(`${API}/api/departements`)
        ]).then(([e, d]) => {
            setEtudiants(e.data);
            setDepartements(d.data);
            setError(null);
        }).catch(err => {
            setError(err.message);
        }).finally(() => setLoading(false));
    };

    useEffect(() => { load(); }, []);

    const resetForm = () => {
        setForm({ cin: "", nom: "", dateNaissance: "", email: "", anneePremiereInscription: "", departementId: "" });
        setEditId(null);
        setShowForm(false);
    };

    const submit = async () => {
        setSaving(true);
        const data = {
            ...form,
            anneePremiereInscription: parseInt(form.anneePremiereInscription) || 0,
            departementId: form.departementId ? parseInt(form.departementId) : null
        };
        try {
            editId
                ? await axios.put(`${API}/api/etudiants/${editId}`, data)
                : await axios.post(`${API}/api/etudiants`, data);
            load();
            resetForm();
        } catch (err: any) {
            alert("Erreur: " + (err.response?.data?.error || err.message));
        } finally {
            setSaving(false);
        }
    };

    const remove = async (id: number, nom: string) => {
        if (!confirm(`Supprimer "${nom}" ?`)) return;
        await axios.delete(`${API}/api/etudiants/${id}`);
        load();
    };

    const edit = (e: any) => {
        setForm({
            cin: e.cin || "", nom: e.nom || "",
            dateNaissance: e.dateNaissance || "",
            email: e.email || "",
            anneePremiereInscription: String(e.anneePremiereInscription || ""),
            departementId: String(e.departementId || "")
        });
        setEditId(e.id);
        setShowForm(true);
        window.scrollTo({ top: 0, behavior: "smooth" });
    };

    if (loading) return (
        <div style={{padding: "2rem"}}>
            <div style={{maxWidth: "1100px", margin: "0 auto"}}>
                <div style={{marginBottom: "24px"}}>
                    <div className="skeleton" style={{width: "200px", height: "32px", marginBottom: "8px"}} />
                    <div className="skeleton" style={{width: "300px", height: "20px"}} />
                </div>
                {[...Array(5)].map((_, i) => (
                    <div key={i} className="skeleton" style={{height: "56px", marginBottom: "8px"}} />
                ))}
            </div>
        </div>
    );

    return (
        <div style={{padding: "2rem", maxWidth: "1100px", margin: "0 auto"}}>

            {/* Header */}
            <div className="fade-in" style={{display: "flex", alignItems: "flex-start", justifyContent: "space-between", marginBottom: "28px", flexWrap: "wrap", gap: "16px"}}>
                <div>
                    <h1 style={{fontSize: "1.75rem", fontWeight: 700, letterSpacing: "-0.03em", marginBottom: "6px"}}>
                        Étudiants
                        <span style={{
                            marginLeft: "12px", fontSize: "0.9rem", fontWeight: 500,
                            background: "var(--accent-bg)", color: "#a5b4fc",
                            padding: "3px 10px", borderRadius: "20px",
                            border: "1px solid var(--accent-border)"
                        }}>{etudiants.length}</span>
                    </h1>
                    <p style={{color: "var(--text-muted)", fontSize: "0.875rem"}}>
                        Gérez les inscriptions et profils étudiants
                    </p>
                </div>
                <button className="btn-primary" onClick={() => { resetForm(); setShowForm(!showForm); }}
                        style={{display: "flex", alignItems: "center", gap: "8px"}}>
                    {showForm ? "✕ Fermer" : "+ Nouvel étudiant"}
                </button>
            </div>

            {/* Error */}
            {error && (
                <div className="fade-in card" style={{
                    padding: "16px 20px", marginBottom: "20px",
                    borderColor: "rgba(239,68,68,0.3)",
                    background: "rgba(239,68,68,0.05)",
                    display: "flex", alignItems: "center", gap: "12px"
                }}>
                    <span style={{fontSize: "1.2rem"}}>⚠️</span>
                    <div>
                        <div style={{color: "#fca5a5", fontWeight: 500, fontSize: "0.875rem"}}>Erreur de connexion</div>
                        <div style={{color: "var(--text-muted)", fontSize: "0.8rem"}}>{error}</div>
                    </div>
                    <button className="btn-secondary" onClick={load} style={{marginLeft: "auto"}}>Réessayer</button>
                </div>
            )}

            {/* Form */}
            {showForm && (
                <div className="fade-in card" style={{padding: "24px", marginBottom: "24px", borderColor: "var(--accent-border)"}}>
                    <h2 style={{fontWeight: 600, fontSize: "1rem", marginBottom: "20px", color: "#a5b4fc"}}>
                        {editId ? "✏️ Modifier l'étudiant" : "✨ Nouvel étudiant"}
                    </h2>
                    <div style={{display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))", gap: "12px", marginBottom: "16px"}}>
                        {[
                            {key: "cin", placeholder: "CIN", label: "CIN"},
                            {key: "nom", placeholder: "Nom complet", label: "Nom"},
                            {key: "email", placeholder: "email@example.com", label: "Email", type: "email"},
                            {key: "anneePremiereInscription", placeholder: "2022", label: "Année d'inscription"},
                        ].map(({key, placeholder, label, type}) => (
                            <div key={key}>
                                <label style={{fontSize: "0.75rem", color: "var(--text-muted)", display: "block", marginBottom: "6px", textTransform: "uppercase", letterSpacing: "0.05em"}}>{label}</label>
                                <input
                                    type={type || "text"}
                                    placeholder={placeholder}
                                    value={(form as any)[key]}
                                    onChange={e => setForm({...form, [key]: e.target.value})}
                                    className="input-field"
                                />
                            </div>
                        ))}
                        <div>
                            <label style={{fontSize: "0.75rem", color: "var(--text-muted)", display: "block", marginBottom: "6px", textTransform: "uppercase", letterSpacing: "0.05em"}}>Date de naissance</label>
                            <input type="date" value={form.dateNaissance}
                                   onChange={e => setForm({...form, dateNaissance: e.target.value})}
                                   className="input-field"
                            />
                        </div>
                        <div>
                            <label style={{fontSize: "0.75rem", color: "var(--text-muted)", display: "block", marginBottom: "6px", textTransform: "uppercase", letterSpacing: "0.05em"}}>Département</label>
                            <select value={form.departementId}
                                    onChange={e => setForm({...form, departementId: e.target.value})}
                                    className="input-field">
                                <option value="">— Aucun —</option>
                                {departements.map((d: any) => (
                                    <option key={d.id} value={d.id}>{d.nom}</option>
                                ))}
                            </select>
                        </div>
                    </div>
                    <div style={{display: "flex", gap: "10px"}}>
                        <button className="btn-primary" onClick={submit} disabled={saving}>
                            {saving ? "Enregistrement..." : editId ? "✓ Mettre à jour" : "✓ Créer l'étudiant"}
                        </button>
                        <button className="btn-secondary" onClick={resetForm}>Annuler</button>
                    </div>
                </div>
            )}

            {/* Table */}
            <div className="fade-in fade-in-1 card" style={{overflow: "hidden"}}>
                {etudiants.length === 0 ? (
                    <div style={{padding: "60px", textAlign: "center", color: "var(--text-muted)"}}>
                        <div style={{fontSize: "3rem", marginBottom: "12px"}}>👤</div>
                        <div style={{fontWeight: 500, marginBottom: "6px"}}>Aucun étudiant</div>
                        <div style={{fontSize: "0.875rem"}}>Ajoutez votre premier étudiant ci-dessus</div>
                    </div>
                ) : (
                    <table style={{width: "100%", borderCollapse: "collapse"}}>
                        <thead>
                        <tr style={{borderBottom: "1px solid var(--border)"}}>
                            {["CIN", "NOM", "EMAIL", "DÉPARTEMENT", "INSCRIPTION", "ACTIONS"].map(h => (
                                <th key={h} style={{
                                    padding: "14px 16px",
                                    textAlign: "left",
                                    fontSize: "0.7rem",
                                    letterSpacing: "0.08em",
                                    color: "var(--text-muted)",
                                    fontWeight: 600,
                                    background: "rgba(255,255,255,0.02)"
                                }}>{h}</th>
                            ))}
                        </tr>
                        </thead>
                        <tbody>
                        {etudiants.map((e: any, i) => (
                            <tr key={e.id} className="table-row fade-in" style={{animationDelay: `${i * 0.04}s`}}>
                                <td style={{padding: "14px 16px"}}>
                    <span style={{
                        fontFamily: "'JetBrains Mono', monospace",
                        fontSize: "0.8rem",
                        color: "#a5b4fc",
                        background: "var(--accent-bg)",
                        padding: "3px 8px",
                        borderRadius: "6px"
                    }}>{e.cin}</span>
                                </td>
                                <td style={{padding: "14px 16px", fontWeight: 500}}>{e.nom}</td>
                                <td style={{padding: "14px 16px", color: "var(--text-secondary)", fontSize: "0.875rem"}}>{e.email || "—"}</td>
                                <td style={{padding: "14px 16px"}}>
                                    {e.departementNom ? (
                                        <span className="badge badge-green">🏛️ {e.departementNom}</span>
                                    ) : (
                                        <span style={{color: "var(--text-muted)", fontSize: "0.8rem"}}>—</span>
                                    )}
                                </td>
                                <td style={{padding: "14px 16px", color: "var(--text-secondary)", fontSize: "0.875rem", fontFamily: "'JetBrains Mono', monospace"}}>{e.anneePremiereInscription || "—"}</td>
                                <td style={{padding: "14px 16px"}}>
                                    <div style={{display: "flex", gap: "6px"}}>
                                        <button className="btn-secondary" onClick={() => edit(e)}>✏️</button>
                                        <button className="btn-danger" onClick={() => remove(e.id, e.nom)}>🗑️</button>
                                    </div>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>
        </div>
    );
}
