"use client";
import { useEffect, useState } from "react";
import axios from "axios";

const API = "http://localhost:8081";

export default function DepartementsPage() {
    const [departements, setDepartements] = useState<any[]>([]);
    const [nom, setNom] = useState("");
    const [editId, setEditId] = useState<number | null>(null);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const load = () => {
        setLoading(true);
        axios.get(`${API}/api/departements`)
            .then(r => { setDepartements(r.data); setError(null); })
            .catch(err => setError(err.message))
            .finally(() => setLoading(false));
    };

    useEffect(() => { load(); }, []);

    const submit = async () => {
        if (!nom.trim()) return;
        setSaving(true);
        try {
            editId
                ? await axios.put(`${API}/api/departements/${editId}`, { nom })
                : await axios.post(`${API}/api/departements`, { nom });
            setNom(""); setEditId(null);
            load();
        } catch (err: any) {
            alert("Erreur: " + err.message);
        } finally {
            setSaving(false);
        }
    };

    const remove = async (id: number, nom: string) => {
        if (!confirm(`Supprimer le département "${nom}" ?`)) return;
        await axios.delete(`${API}/api/departements/${id}`);
        load();
    };

    return (
        <div style={{padding: "2rem", maxWidth: "700px", margin: "0 auto"}}>

            {/* Header */}
            <div className="fade-in" style={{marginBottom: "28px"}}>
                <h1 style={{fontSize: "1.75rem", fontWeight: 700, letterSpacing: "-0.03em", marginBottom: "6px"}}>
                    Départements
                    {!loading && (
                        <span style={{
                            marginLeft: "12px", fontSize: "0.9rem", fontWeight: 500,
                            background: "rgba(16,185,129,0.1)", color: "#34d399",
                            padding: "3px 10px", borderRadius: "20px",
                            border: "1px solid rgba(16,185,129,0.2)"
                        }}>{departements.length}</span>
                    )}
                </h1>
                <p style={{color: "var(--text-muted)", fontSize: "0.875rem"}}>
                    Organisez vos étudiants par département
                </p>
            </div>

            {/* Add / Edit form */}
            <div className="fade-in fade-in-1 card" style={{padding: "20px", marginBottom: "24px"}}>
                <label style={{
                    fontSize: "0.75rem", color: "var(--text-muted)",
                    display: "block", marginBottom: "10px",
                    textTransform: "uppercase", letterSpacing: "0.05em"
                }}>
                    {editId ? "✏️ Modifier le département" : "✨ Nouveau département"}
                </label>
                <div style={{display: "flex", gap: "10px"}}>
                    <input
                        value={nom}
                        onChange={e => setNom(e.target.value)}
                        onKeyDown={e => e.key === "Enter" && submit()}
                        placeholder="Ex: Informatique, Mathématiques..."
                        className="input-field"
                        style={{flex: 1}}
                    />
                    <button className="btn-primary" onClick={submit} disabled={saving || !nom.trim()}>
                        {saving ? "..." : editId ? "✓ Modifier" : "+ Ajouter"}
                    </button>
                    {editId && (
                        <button className="btn-secondary" onClick={() => { setEditId(null); setNom(""); }}>
                            ✕
                        </button>
                    )}
                </div>
            </div>

            {/* Error */}
            {error && (
                <div className="fade-in card" style={{
                    padding: "14px 18px", marginBottom: "20px",
                    borderColor: "rgba(239,68,68,0.3)",
                    background: "rgba(239,68,68,0.05)",
                    color: "#fca5a5", fontSize: "0.875rem",
                    display: "flex", alignItems: "center", gap: "8px"
                }}>
                    ⚠️ {error}
                    <button className="btn-secondary" onClick={load} style={{marginLeft: "auto", fontSize: "0.75rem"}}>Réessayer</button>
                </div>
            )}

            {/* List */}
            {loading ? (
                <div style={{display: "flex", flexDirection: "column", gap: "8px"}}>
                    {[...Array(3)].map((_, i) => (
                        <div key={i} className="skeleton" style={{height: "64px"}} />
                    ))}
                </div>
            ) : departements.length === 0 ? (
                <div className="fade-in card" style={{
                    padding: "48px", textAlign: "center", color: "var(--text-muted)"
                }}>
                    <div style={{fontSize: "2.5rem", marginBottom: "12px"}}>🏛️</div>
                    <div style={{fontWeight: 500, marginBottom: "6px"}}>Aucun département</div>
                    <div style={{fontSize: "0.875rem"}}>Créez votre premier département</div>
                </div>
            ) : (
                <div style={{display: "flex", flexDirection: "column", gap: "8px"}}>
                    {departements.map((d: any, i) => (
                        <div key={d.id} className="fade-in card" style={{
                            padding: "16px 20px",
                            display: "flex", alignItems: "center", justifyContent: "space-between",
                            animationDelay: `${i * 0.06}s`,
                            cursor: "default"
                        }}>
                            <div style={{display: "flex", alignItems: "center", gap: "12px"}}>
                                <div style={{
                                    width: "38px", height: "38px",
                                    background: `hsl(${(d.id * 47) % 360}, 60%, 20%)`,
                                    border: `1px solid hsl(${(d.id * 47) % 360}, 60%, 35%)`,
                                    borderRadius: "10px",
                                    display: "flex", alignItems: "center", justifyContent: "center",
                                    fontSize: "1rem"
                                }}>
                                    🏛️
                                </div>
                                <div>
                                    <div style={{fontWeight: 600, marginBottom: "2px"}}>{d.nom}</div>
                                    <div style={{
                                        fontSize: "0.72rem",
                                        color: "var(--text-muted)",
                                        fontFamily: "'JetBrains Mono', monospace"
                                    }}>ID #{d.id}</div>
                                </div>
                            </div>

                            <div style={{display: "flex", gap: "6px"}}>
                                <button className="btn-secondary"
                                        onClick={() => { setEditId(d.id); setNom(d.nom); window.scrollTo({top: 0, behavior: "smooth"}); }}
                                        style={{fontSize: "0.8rem"}}>
                                    ✏️ Modifier
                                </button>
                                <button className="btn-danger"
                                        onClick={() => remove(d.id, d.nom)}
                                        style={{fontSize: "0.8rem"}}>
                                    🗑️ Supprimer
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}
