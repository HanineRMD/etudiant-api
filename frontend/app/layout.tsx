import type { Metadata } from "next";
import "./globals.css";
import Link from "next/link";

export const metadata: Metadata = { title: "Projet Etudiants" };

export default function RootLayout({
                                     children,
                                   }: {
  children: React.ReactNode;
}) {
  return (
      <html lang="fr">
      <body className="bg-gray-100 min-h-screen">
      <nav className="bg-blue-700 text-white p-4 flex gap-8 shadow">
        <span className="font-bold text-lg">Projet Etudiants</span>
        <Link href="/etudiants" className="hover:underline">
          Etudiants
        </Link>
        <Link href="/departements" className="hover:underline">
          Departements
        </Link>
      </nav>
      <main className="p-6">{children}</main>
      </body>
      </html>
  );
}