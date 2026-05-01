import Link from "next/link";

export default function Home() {
    return (
        <div style={{
            minHeight: "calc(100vh - 64px)",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            padding: "2rem",
            textAlign: "center"
        }}>
            <div className="fade-in" style={{marginBottom: "24px"}}>
                <div style={{
                    width: "80px", height: "80px",
                    background: "linear-gradient(135deg, #6366f1, #8b5cf6)",
                    borderRadius: "24px",
                    display: "flex", alignItems: "center", justifyContent: "center",
                    margin: "0 auto 24px",
                    boxShadow: "0 8px 32px rgba(99,102,241,0.3)",
                    fontSize: "36px"
                }}>🎓</div>

                <h1 style={{
                    fontSize: "clamp(2rem, 5vw, 3.5rem)",
                    fontWeight: 700,
                    letterSpacing: "-0.04em",
                    lineHeight: 1.1,
                    marginBottom: "16px",
                    background: "linear-gradient(135deg, #f1f5f9, #94a3b8)",
                    WebkitBackgroundClip: "text",
                    WebkitTextFillColor: "transparent"
                }}>
                    Gestion des Étudiants
                </h1>

                <p style={{color: "var(--text-secondary)", fontSize: "1.1rem", marginBottom: "40px", maxWidth: "480px"}}>
                    Plateforme de gestion complète pour étudiants et départements avec architecture microservices.
                </p>
            </div>

            <div className="fade-in fade-in-1" style={{display: "flex", gap: "16px", flexWrap: "wrap", justifyContent: "center"}}>
                <Link href="/etudiants" style={{
                    display: "flex", alignItems: "center", gap: "10px",
                    padding: "14px 28px",
                    background: "linear-gradient(135deg, #6366f1, #8b5cf6)",
                    color: "white", textDecoration: "none",
                    borderRadius: "12px", fontWeight: 600, fontSize: "0.95rem",
                    boxShadow: "0 4px 20px rgba(99,102,241,0.3)",
                    transition: "all 0.2s"
                }}>
                    👨‍🎓 Voir les Étudiants
                </Link>
                <Link href="/departements" style={{
                    display: "flex", alignItems: "center", gap: "10px",
                    padding: "14px 28px",
                    background: "rgba(255,255,255,0.05)",
                    border: "1px solid rgba(255,255,255,0.1)",
                    color: "var(--text-primary)", textDecoration: "none",
                    borderRadius: "12px", fontWeight: 600, fontSize: "0.95rem",
                    transition: "all 0.2s"
                }}>
                    🏛️ Gérer les Départements
                </Link>
            </div>

            <div className="fade-in fade-in-2" style={{
                display: "flex", gap: "32px", marginTop: "60px",
                color: "var(--text-muted)", fontSize: "0.8rem",
                fontFamily: "'JetBrains Mono', monospace"
            }}>
                {[
                    {dot: "#10b981", label: "Spring Boot API"},
                    {dot: "#6366f1", label: "Eureka Registry"},
                    {dot: "#f59e0b", label: "PostgreSQL"},
                    {dot: "#8b5cf6", label: "Redis Cache"},
                ].map(({dot, label}) => (
                    <div key={label} style={{display: "flex", alignItems: "center", gap: "6px"}}>
                        <div style={{
                            width: "7px", height: "7px", borderRadius: "50%",
                            background: dot, animation: "pulse-dot 2s infinite"
                        }}/>
                        {label}
                    </div>
                ))}
            </div>
        </div>
    );
}
