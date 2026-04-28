"use client";
import { useEffect, useState } from "react";
import axios from "axios";

const API = "http://localhost:8080";

export default function DepartementsPage() {
    const [departements, setDepartements] = useState([]);
    const [nom, setNom] = useState("");
    const [editId, setEditId] = useState<number | null>(null);

    const load = () => {
        axios.get(`${API}/api/departements`).then(r => setDepartements(r.data));
    };

    useEffect(() => { load(); }, []);

    const submit = () => {
        if (!nom.trim()) return;
        const req = editId
            ? axios.put(`${API}/api/departements/${editId}`, { nom })
            : axios.post(`${API}/api/departements`, { nom });
        req.then(() => { setNom(""); setEditId(null); load(); });
    };

    const remove = (id: number) => {
        axios.delete(`${API}/api/departements/${id}`).then(load);
    };

    return (
        <div className="max-w-xl">
            <h1 className="text-2xl font-bold mb-6">Departements</h1>

            <div className="flex gap-3 mb-6">
                <input value={nom} onChange={e => setNom(e.target.value)}
                       placeholder="Nom du departement"
                       className="border rounded px-3 py-2 flex-1" />
                <button onClick={submit}
                        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
                    {editId ? "Modifier" : "Ajouter"}
                </button>
                {editId && (
                    <button onClick={() => { setEditId(null); setNom(""); }}
                            className="bg-gray-400 text-white px-4 py-2 rounded">
                        Annuler
                    </button>
                )}
            </div>

            <ul className="space-y-2">
                {departements.map((d: any) => (
                    <li key={d.id}
                        className="flex justify-between items-center bg-white p-4 rounded shadow">
                        <span className="font-medium">{d.nom}</span>
                        <div className="flex gap-3">
                            <button onClick={() => { setEditId(d.id); setNom(d.nom); }}
                                    className="text-blue-600 hover:underline">Modifier</button>
                            <button onClick={() => remove(d.id)}
                                    className="text-red-600 hover:underline">Supprimer</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
}