import Link from "next/link";

export default function Home() {
  return (
      <div className="text-center mt-20">
        <h1 className="text-4xl font-bold mb-6">Gestion des Etudiants</h1>
        <div className="flex gap-4 justify-center">
          <Link href="/etudiants"
                className="bg-blue-600 text-white px-6 py-3 rounded hover:bg-blue-700">
            Etudiants
          </Link>
          <Link href="/departements"
                className="bg-green-600 text-white px-6 py-3 rounded hover:bg-green-700">
            Departements
          </Link>
        </div>
      </div>
  );
}