"use client";

import "./globals.css";
import Link from "next/link";
import { GraduationCap } from "lucide-react";

// Remove the metadata export - it doesn't work in Client Components
// export const metadata: Metadata = { title: "EduManager — Gestion des Étudiants" };

export default function RootLayout({ children }: { children: React.ReactNode }) {
    return (
        <html lang="fr">
        <head>
            <title>EduManager — Gestion des Étudiants</title>
            <link href="https://fonts.googleapis.com/css2?family=Sora:wght@300;400;500;600;700&family=JetBrains+Mono:wght@400;500&display=swap" rel="stylesheet" />
        </head>
        <body className="min-h-screen" style={{background: "var(--bg-main)", fontFamily: "'Sora', sans-serif"}}>
        <nav style={{
            background: "var(--nav-bg)",
            borderBottom: "1px solid var(--border)",
            backdropFilter: "blur(20px)",
            position: "sticky",
            top: 0,
            zIndex: 100,
            padding: "0 2rem",
            height: "64px",
            display: "flex",
            alignItems: "center",
            justifyContent: "space-between"
        }}>
            <Link href="/" style={{display: "flex", alignItems: "center", gap: "10px", textDecoration: "none"}}>
                <div style={{
                    width: "36px", height: "36px",
                    background: "linear-gradient(135deg, var(--accent), var(--accent-2))",
                    borderRadius: "10px",
                    display: "flex", alignItems: "center", justifyContent: "center"
                }}>
                    <span style={{color: "white", fontSize: "18px"}}>🎓</span>
                </div>
                <span style={{fontWeight: 700, fontSize: "1.1rem", color: "var(--text-primary)", letterSpacing: "-0.02em"}}>
              EduManager
            </span>
            </Link>

            <div style={{display: "flex", gap: "4px"}}>
                {[
                    { href: "/etudiants", label: "👨‍🎓 Étudiants" },
                    { href: "/departements", label: "🏛️ Départements" },
                ].map(({ href, label }) => (
                    <Link key={href} href={href} style={{
                        padding: "8px 16px",
                        borderRadius: "8px",
                        textDecoration: "none",
                        color: "var(--text-secondary)",
                        fontSize: "0.875rem",
                        fontWeight: 500,
                        transition: "all 0.15s",
                    }}
                          onMouseEnter={e => {
                              (e.target as HTMLElement).style.background = "var(--hover-bg)";
                              (e.target as HTMLElement).style.color = "var(--text-primary)";
                          }}
                          onMouseLeave={e => {
                              (e.target as HTMLElement).style.background = "transparent";
                              (e.target as HTMLElement).style.color = "var(--text-secondary)";
                          }}>
                        {label}
                    </Link>
                ))}
            </div>

            <div style={{
                fontSize: "0.75rem",
                fontFamily: "'JetBrains Mono', monospace",
                color: "var(--accent)",
                background: "var(--accent-bg)",
                padding: "4px 10px",
                borderRadius: "20px",
                border: "1px solid var(--accent-border)"
            }}>
                API: localhost:8081
            </div>
        </nav>

        <main style={{minHeight: "calc(100vh - 64px)"}}>
            {children}
        </main>
        </body>
        </html>
    );
}