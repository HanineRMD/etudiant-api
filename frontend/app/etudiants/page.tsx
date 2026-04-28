"use client";
import { useEffect, useState } from "react";
import axios from "axios";

const API = "http://localhost:8080";

export default function EtudiantsPage() {
    const [etudiants, setEtudiants] = useState([]);
    const [departements, setDepartements] = useState([]);
    const [loading, setLoading] = useState(true);
    const [form, setForm] = useState({
        cin: "", nom: "", dateNaissance: "",
        email: "", anneePremiereInscription: "", departementId: ""
    });
    const [editId, setEditId] = useState<number | null>(null);
    const [showForm, setShowForm] = useState(false);

    const load = () => {
        setLoading(true);
        Promise.all([
            axios.get(`${API}/api/etudiants`),
            axios.get(`${API}/api/departements`)
        ]).then(([e, d]) => {
            setEtudiants(e.data);
            setDepartements(d.data);
        }).finally(() => setLoading(false));
    };

    useEffect(() => { load(); }, []);

    const submit = () => {
        const data = { ...form, anneePremiereInscription: parseInt(form.anneePremiereInscription), departementId: form.departementId ? parseInt(form.departementId) : null };
        const req = editId
            ? axios.put(`${API}/api/etudiants/${editId}`, data)
            : axios.post(`${API}/api/etudiants`, data);
        req.then(() => { load(); setShowForm(false); setEditId(null); setForm({ cin: "", nom: "", dateNaissance: "", email: "", anneePremiereInscription: "", departementId: "" }); });
    };

    const remove = (id: number) => {
        axios.delete(`${API}/api/etudiants/${id}`).then(load);
    };

    const edit = (e: any) => {
        setForm({ cin: e.cin, nom: e.nom, dateNaissance: e.dateNaissance, email: e.email, anneePremiereInscription: e.anneePremiereInscription, departementId: e.departementId || "" });
        setEditId(e.id);
        setShowForm(true);
    };

    if (loading) return <div className="text-center mt-10">Chargement...</div>;

    return (
        <div>
            <div className="flex justify-between mb-4">
                <h1 className="text-2xl font-bold">Etudiants</h1>
                <button onClick={() => setShowForm(!showForm)}
                        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
                    + Ajouter
                </button>
            </div>

            {showForm && (
                <div className="bg-white p-4 rounded shadow mb-6 grid grid-cols-2 gap-3">
                    <input placeholder="CIN" value={form.cin}
                           onChange={e => setForm({ ...form, cin: e.target.value })}
                           className="border p-2 rounded" />
                    <input placeholder="Nom" value={form.nom}
                           onChange={e => setForm({ ...form, nom: e.target.value })}
                           className="border p-2 rounded" />
                    <input placeholder="Date naissance (YYYY-MM-DD)" value={form.dateNaissance}
                           onChange={e => setForm({ ...form, dateNaissance: e.target.value })}
                           className="border p-2 rounded" />
                    <input placeholder="Email" value={form.email}
                           onChange={e => setForm({ ...form, email: e.target.value })}
                           className="border p-2 rounded" />
                    <input placeholder="Annee inscription" value={form.anneePremiereInscription}
                           onChange={e => setForm({ ...form, anneePremiereInscription: e.target.value })}
                           className="border p-2 rounded" />
                    <select value={form.departementId}
                            onChange={e => setForm({ ...form, departementId: e.target.value })}
                            className="border p-2 rounded">
                        <option value="">-- Departement --</option>
                        {departements.map((d: any) => (
                            <option key={d.id} value={d.id}>{d.nom}</option>
                        ))}
                    </select>
                    <button onClick={submit}
                            className="col-span-2 bg-green-600 text-white py-2 rounded hover:bg-green-700">
                        {editId ? "Modifier" : "Creer"}
                    </button>
                </div>
            )}

            <div className="bg-white rounded shadow overflow-hidden">
                <table className="w-full">
                    <thead className="bg-blue-600 text-white">
                    <tr>
                        <th className="p-3 text-left">CIN</th>
                        <th className="p-3 text-left">Nom</th>
                        <th className="p-3 text-left">Date naissance</th>
                        <th className="p-3 text-left">Email</th>
                        <th className="p-3 text-left">Departement</th>
                        <th className="p-3 text-left">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {etudiants.map((e: any) => (
                        <tr key={e.id} className="border-b hover:bg-gray-50">
                            <td className="p-3">{e.cin}</td>
                            <td className="p-3">{e.nom}</td>
                            <td className="p-3">{e.dateNaissance}</td>
                            <td className="p-3">{e.email || "-"}</td>
                            <td className="p-3">{e.departementNom || "-"}</td>
                            <td className="p-3 flex gap-2">
                                <button onClick={() => edit(e)}
                                        className="text-blue-600 hover:underline">Edit</button>
                                <button onClick={() => remove(e.id)}
                                        className="text-red-600 hover:underline">Suppr</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}