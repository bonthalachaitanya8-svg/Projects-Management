import { Navigate, Outlet, Route, Routes, NavLink, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { motion } from "framer-motion";
import api from "./api";

function Layout() {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem("user") || "null");

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  const items = [
    { label: "Dashboard", path: "/app/dashboard" },
    { label: "Projects", path: "/app/projects" },
    { label: "Create Project", path: "/app/projects/new" },
    ...(user?.role === "ADMIN" ? [{ label: "Users", path: "/app/users" }] : []),
    { label: "Profile", path: "/app/profile" },
  ];

  return (
    <div className="min-h-screen bg-slate-950 text-white xl:flex">
      <aside className="w-full border-b border-white/10 bg-slate-950/80 p-4 backdrop-blur xl:min-h-screen xl:w-72 xl:border-b-0 xl:border-r">
        <div className="rounded-3xl border border-white/10 bg-white/5 p-5">
          <p className="text-xs uppercase tracking-[0.3em] text-indigo-300">Project Universe</p>
          <h1 className="mt-3 text-2xl font-bold text-white">Management Portal</h1>
          <p className="mt-2 text-sm text-slate-400">{user?.fullName} • {user?.role}</p>
        </div>

        <nav className="mt-6 grid gap-2">
          {items.map((item) => (
            <NavLink
              key={item.path}
              to={item.path}
              className={({ isActive }) =>
                `rounded-2xl px-4 py-3 text-sm font-medium transition ${
                  isActive
                    ? "bg-indigo-500 text-white shadow-lg shadow-indigo-500/25"
                    : "bg-white/5 text-slate-300 hover:bg-white/10 hover:text-white"
                }`
              }
            >
              {item.label}
            </NavLink>
          ))}
        </nav>

        <button onClick={logout} className="mt-6 w-full rounded-2xl border border-white/10 px-4 py-3 text-sm hover:bg-white/10">
          Logout
        </button>
      </aside>

      <main className="flex-1 p-4 md:p-6">
        <div className="mx-auto max-w-7xl">
          <div className="flex flex-col gap-4 rounded-3xl border border-white/10 bg-white/5 p-5 md:flex-row md:items-center md:justify-between">
            <div>
              <p className="text-xs uppercase tracking-[0.25em] text-slate-400">Workspace</p>
              <h2 className="mt-2 text-3xl font-bold text-white">Database Ready Portal</h2>
              <p className="mt-2 text-slate-400">Phase 4 uses PostgreSQL + JPA and keeps data after restart.</p>
            </div>
            <div className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-sm text-slate-300">
              {user?.email}
            </div>
          </div>

          <div className="mt-6">
            <Outlet />
          </div>

          <footer className="mt-10 text-center text-sm text-slate-500">
            © 2026 Chaitanya Bonthala. All rights reserved.
          </footer>
        </div>
      </main>
    </div>
  );
}

function LoginPage() {
  const navigate = useNavigate();
  const [mode, setMode] = useState("login");
  const [form, setForm] = useState({
    fullName: "",
    email: "admin@example.com",
    password: "Password@123",
    skills: "",
    profileSummary: "",
  });
  const [error, setError] = useState("");

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const submit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const endpoint = mode === "login" ? "/api/auth/login" : "/api/auth/register";
      const payload = mode === "login"
        ? { email: form.email, password: form.password }
        : form;
      const { data } = await api.post(endpoint, payload);
      localStorage.setItem("token", data.token);
      localStorage.setItem("user", JSON.stringify(data));
      navigate("/app/dashboard");
    } catch (err) {
      setError(err?.response?.data?.message || err?.message || "Request failed");
    }
  };

  return (
    <div className="relative min-h-screen overflow-hidden bg-gradient-to-br from-slate-950 via-indigo-950 to-slate-900 text-white">
      <div className="relative z-10 grid min-h-screen lg:grid-cols-2">
        <div className="flex flex-col justify-center px-8 py-12 lg:px-16">
          <motion.p initial={{ opacity: 0, y: 14 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 0.7 }}
            className="mb-4 inline-flex w-fit rounded-full border border-white/20 bg-white/10 px-4 py-2 text-sm text-indigo-100 backdrop-blur">
            Welcome to your project universe
          </motion.p>

          <motion.h1 initial={{ opacity: 0, y: -22 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 0.9 }}
            className="max-w-xl text-4xl font-bold leading-tight md:text-6xl">
            Start in the universe of projects 🚀
          </motion.h1>

          <p className="mt-6 max-w-lg text-lg text-slate-300">
            This version stores users, projects, file metadata, and GitHub links in PostgreSQL.
          </p>
        </div>

        <div className="flex items-center justify-center px-8 py-12">
          <motion.form onSubmit={submit} initial={{ opacity: 0, y: 45 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 0.85 }}
            className="w-full max-w-md rounded-3xl border border-white/15 bg-white/10 p-8 shadow-2xl backdrop-blur-xl">
            <div className="mb-8 flex items-center justify-between">
              <div>
                <h2 className="text-3xl font-semibold">{mode === "login" ? "Sign in" : "Register"}</h2>
                <p className="mt-2 text-sm text-slate-300">Phase 4 DB starter</p>
              </div>
              <button type="button" onClick={() => setMode(mode === "login" ? "register" : "login")}
                className="rounded-xl border border-white/10 px-3 py-2 text-sm hover:bg-white/10">
                {mode === "login" ? "Register" : "Login"}
              </button>
            </div>

            {mode === "register" && (
              <>
                <label className="mb-2 block text-sm text-slate-200">Full Name</label>
                <input name="fullName" value={form.fullName} onChange={onChange}
                  className="mb-4 w-full rounded-2xl border border-white/15 bg-slate-950/40 px-4 py-3 text-white outline-none focus:border-indigo-400" />
              </>
            )}

            <label className="mb-2 block text-sm text-slate-200">Email</label>
            <input name="email" value={form.email} onChange={onChange}
              className="mb-4 w-full rounded-2xl border border-white/15 bg-slate-950/40 px-4 py-3 text-white outline-none focus:border-indigo-400" />

            <label className="mb-2 block text-sm text-slate-200">Password</label>
            <input type="password" name="password" value={form.password} onChange={onChange}
              className="mb-4 w-full rounded-2xl border border-white/15 bg-slate-950/40 px-4 py-3 text-white outline-none focus:border-indigo-400" />

            {mode === "register" && (
              <>
                <label className="mb-2 block text-sm text-slate-200">Skills</label>
                <input name="skills" value={form.skills} onChange={onChange}
                  className="mb-4 w-full rounded-2xl border border-white/15 bg-slate-950/40 px-4 py-3 text-white outline-none focus:border-indigo-400" />
                <label className="mb-2 block text-sm text-slate-200">Profile Summary</label>
                <textarea name="profileSummary" value={form.profileSummary} onChange={onChange} rows="3"
                  className="mb-4 w-full rounded-2xl border border-white/15 bg-slate-950/40 px-4 py-3 text-white outline-none focus:border-indigo-400" />
              </>
            )}

            {error && <div className="mb-4 rounded-2xl border border-red-400/30 bg-red-500/10 px-4 py-3 text-sm text-red-200">{error}</div>}

            <button className="w-full rounded-2xl bg-indigo-500 px-4 py-3 font-semibold text-white shadow-lg shadow-indigo-500/30 transition hover:bg-indigo-400">
              {mode === "login" ? "Sign in" : "Create account"}
            </button>

            <div className="mt-8 rounded-2xl border border-white/10 bg-slate-950/30 p-4 text-sm text-slate-300">
              Demo admin: <span className="font-medium text-white">admin@example.com</span><br />
              Demo user: <span className="font-medium text-white">user@example.com</span><br />
              Password: <span className="font-medium text-white">Password@123</span>
            </div>
          </motion.form>
        </div>
      </div>

      <footer className="absolute bottom-4 left-0 right-0 z-10 text-center text-sm text-slate-400">
        © 2026 Chaitanya Bonthala. All rights reserved.
      </footer>
    </div>
  );
}

function DashboardPage() {
  const [data, setData] = useState(null);

  useEffect(() => {
    api.get("/api/dashboard").then((r) => setData(r.data)).catch(console.error);
  }, []);

  if (!data) return <div className="text-slate-300">Loading dashboard...</div>;

  const cards = [
    ["Total users", data.totalUsers],
    ["Total projects", data.totalProjects],
    ["Total files", data.totalFiles],
    ["Total links", data.totalLinks],
    ["Active projects", data.activeProjects],
    ["Completed projects", data.completedProjects],
  ];

  return (
    <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
      {cards.map(([label, value]) => (
        <div key={label} className="rounded-3xl border border-white/10 bg-gradient-to-br from-white/10 to-white/5 p-5">
          <p className="text-sm text-slate-400">{label}</p>
          <p className="mt-4 text-4xl font-bold text-white">{value}</p>
        </div>
      ))}
    </div>
  );
}

function ProjectsPage() {
  const [projects, setProjects] = useState([]);
  const [fileForm, setFileForm] = useState({});
  const [linkForm, setLinkForm] = useState({});
  const [message, setMessage] = useState("");

  const load = () => api.get("/api/projects").then((r) => setProjects(r.data)).catch(console.error);

  useEffect(() => { load(); }, []);

  const addFile = async (projectId) => {
    try {
      await api.post(`/api/projects/${projectId}/files`, {
        fileName: fileForm[projectId]?.fileName || "",
        fileUrl: fileForm[projectId]?.fileUrl || "",
        fileType: fileForm[projectId]?.fileType || "FILE",
        folderName: fileForm[projectId]?.folderName || "",
        relativePath: fileForm[projectId]?.relativePath || ""
      });
      setMessage("File metadata added.");
      load();
    } catch {
      setMessage("Failed to add file metadata.");
    }
  };

  const addLink = async (projectId) => {
    try {
      await api.post(`/api/projects/${projectId}/links`, {
        githubUrl: linkForm[projectId]?.githubUrl || "",
        description: linkForm[projectId]?.description || ""
      });
      setMessage("GitHub link added.");
      load();
    } catch {
      setMessage("Failed to add GitHub link.");
    }
  };

  return (
    <div className="space-y-6">
      {message && <div className="rounded-2xl border border-white/10 bg-white/5 px-4 py-3 text-slate-300">{message}</div>}
      {projects.map((p) => (
        <div key={p.id} className="rounded-3xl border border-white/10 bg-white/5 p-6">
          <div className="flex flex-col gap-4 md:flex-row md:items-start md:justify-between">
            <div>
              <h3 className="text-2xl font-semibold text-white">{p.title}</h3>
              <p className="mt-2 text-slate-400">{p.description}</p>
              <div className="mt-3 text-sm text-slate-300">Owner: {p.ownerName} • Status: {p.status}</div>
            </div>
            <div className="rounded-full bg-indigo-500/20 px-3 py-1 text-xs text-indigo-200">{p.active ? "ACTIVE" : "INACTIVE"}</div>
          </div>

          <div className="mt-6 grid gap-6 xl:grid-cols-2">
            <div className="rounded-2xl border border-white/10 bg-slate-950/30 p-4">
              <h4 className="font-semibold text-white">File metadata</h4>
              <div className="mt-3 grid gap-3">
                <input placeholder="File name" className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none"
                  onChange={(e) => setFileForm({ ...fileForm, [p.id]: { ...fileForm[p.id], fileName: e.target.value } })} />
                <input placeholder="File URL" className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none"
                  onChange={(e) => setFileForm({ ...fileForm, [p.id]: { ...fileForm[p.id], fileUrl: e.target.value } })} />
                <input placeholder="Folder name" className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none"
                  onChange={(e) => setFileForm({ ...fileForm, [p.id]: { ...fileForm[p.id], folderName: e.target.value } })} />
                <input placeholder="Relative path" className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none"
                  onChange={(e) => setFileForm({ ...fileForm, [p.id]: { ...fileForm[p.id], relativePath: e.target.value } })} />
                <button onClick={() => addFile(p.id)} className="rounded-2xl bg-indigo-500 px-4 py-3 font-semibold text-white hover:bg-indigo-400">
                  Add file metadata
                </button>
              </div>

              <div className="mt-4 space-y-2">
                {(p.files || []).map((file) => (
                  <div key={file.id} className="rounded-xl border border-white/10 bg-white/5 p-3 text-sm text-slate-300">
                    <div className="font-medium text-white">{file.fileName}</div>
                    <div>{file.folderName || "-"}</div>
                    <div>{file.relativePath || "-"}</div>
                  </div>
                ))}
              </div>
            </div>

            <div className="rounded-2xl border border-white/10 bg-slate-950/30 p-4">
              <h4 className="font-semibold text-white">GitHub links</h4>
              <div className="mt-3 grid gap-3">
                <input placeholder="GitHub URL" className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none"
                  onChange={(e) => setLinkForm({ ...linkForm, [p.id]: { ...linkForm[p.id], githubUrl: e.target.value } })} />
                <input placeholder="Description" className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none"
                  onChange={(e) => setLinkForm({ ...linkForm, [p.id]: { ...linkForm[p.id], description: e.target.value } })} />
                <button onClick={() => addLink(p.id)} className="rounded-2xl bg-indigo-500 px-4 py-3 font-semibold text-white hover:bg-indigo-400">
                  Add GitHub link
                </button>
              </div>

              <div className="mt-4 space-y-2">
                {(p.links || []).map((link) => (
                  <div key={link.id} className="rounded-xl border border-white/10 bg-white/5 p-3 text-sm text-slate-300">
                    <div className="font-medium text-white break-all">{link.githubUrl}</div>
                    <div>{link.description || "-"}</div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}

function ProjectFormPage() {
  const user = JSON.parse(localStorage.getItem("user") || "null");
  const [form, setForm] = useState({ title: "", description: "", status: "PLANNING", startDate: "", endDate: "", ownerId: user?.userId || 2 });
  const [msg, setMsg] = useState("");

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const onSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post("/api/projects", { ...form, ownerId: Number(form.ownerId) });
      setMsg("Project created successfully.");
      setForm({ ...form, title: "", description: "", status: "PLANNING", startDate: "", endDate: "" });
    } catch (err) {
      setMsg(err?.response?.data?.message || "Failed to create project.");
    }
  };

  return (
    <div className="rounded-3xl border border-white/10 bg-white/5 p-6">
      <h3 className="text-2xl font-semibold text-white">Create Project</h3>
      <form onSubmit={onSubmit} className="mt-4 grid gap-4 md:grid-cols-2">
        <div className="md:col-span-2">
          <input name="title" value={form.title} onChange={onChange} placeholder="Project title"
            className="w-full rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none focus:border-indigo-400" />
        </div>
        <div className="md:col-span-2">
          <textarea name="description" value={form.description} onChange={onChange} rows="4" placeholder="Description"
            className="w-full rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none focus:border-indigo-400" />
        </div>
        <select name="status" value={form.status} onChange={onChange}
          className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none focus:border-indigo-400">
          <option value="PLANNING">PLANNING</option>
          <option value="IN_PROGRESS">IN_PROGRESS</option>
          <option value="COMPLETED">COMPLETED</option>
          <option value="ON_HOLD">ON_HOLD</option>
        </select>

        {user?.role === "ADMIN" ? (
          <select name="ownerId" value={form.ownerId} onChange={onChange}
            className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none focus:border-indigo-400">
            <option value="1">1 - Chaitanya Admin</option>
            <option value="2">2 - Chaitanya User</option>
          </select>
        ) : (
          <input value={`${user?.fullName || ""} (auto assigned)`} readOnly
            className="rounded-2xl border border-white/10 bg-slate-900/60 px-4 py-3 text-slate-300 outline-none" />
        )}

        <input type="date" name="startDate" value={form.startDate} onChange={onChange}
          className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none focus:border-indigo-400" />
        <input type="date" name="endDate" value={form.endDate} onChange={onChange}
          className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none focus:border-indigo-400" />

        <div className="md:col-span-2 flex items-center gap-4">
          <button className="rounded-2xl bg-indigo-500 px-5 py-3 font-semibold text-white transition hover:bg-indigo-400">Save Project</button>
          {msg && <span className="text-sm text-slate-300">{msg}</span>}
        </div>
      </form>
    </div>
  );
}

function UsersPage() {
  const [users, setUsers] = useState([]);
  const user = JSON.parse(localStorage.getItem("user") || "null");

  useEffect(() => {
    if (user?.role === "ADMIN") {
      api.get("/api/users").then((r) => setUsers(r.data)).catch(console.error);
    }
  }, [user?.role]);

  if (user?.role !== "ADMIN") {
    return <div className="rounded-3xl border border-white/10 bg-white/5 p-6 text-slate-300">Only admins can view all users.</div>;
  }

  return (
    <div className="grid gap-4 md:grid-cols-2">
      {users.map((u) => (
        <div key={u.id} className="rounded-2xl border border-white/10 bg-slate-950/40 p-5">
          <div className="flex items-start justify-between gap-4">
            <div>
              <p className="text-xl font-semibold text-white">{u.fullName}</p>
              <p className="mt-1 text-sm text-slate-400">{u.email}</p>
            </div>
            <span className="rounded-full bg-indigo-500/20 px-3 py-1 text-xs text-indigo-200">{u.role}</span>
          </div>
          <p className="mt-4 text-sm text-slate-300">{u.profileSummary}</p>
          <p className="mt-3 text-sm text-slate-400">Skills: {u.skills}</p>
        </div>
      ))}
    </div>
  );
}

function ProfilePage() {
  const [profile, setProfile] = useState(null);
  const [form, setForm] = useState({ fullName: "", skills: "", profileSummary: "" });
  const [msg, setMsg] = useState("");

  useEffect(() => {
    api.get("/api/users/me").then((r) => {
      setProfile(r.data);
      setForm({
        fullName: r.data.fullName || "",
        skills: r.data.skills || "",
        profileSummary: r.data.profileSummary || "",
      });
    }).catch(console.error);
  }, []);

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const save = async (e) => {
    e.preventDefault();
    try {
      const { data } = await api.put("/api/users/me", form);
      setProfile(data);
      const current = JSON.parse(localStorage.getItem("user") || "null");
      if (current) {
        current.fullName = data.fullName;
        localStorage.setItem("user", JSON.stringify(current));
      }
      setMsg("Profile updated.");
    } catch {
      setMsg("Failed to update profile.");
    }
  };

  if (!profile) return <div className="text-slate-300">Loading profile...</div>;

  return (
    <div className="grid gap-6 xl:grid-cols-2">
      <div className="rounded-3xl border border-white/10 bg-white/5 p-6">
        <h3 className="text-2xl font-semibold text-white">My Profile</h3>
        <div className="mt-4 space-y-3 text-slate-300">
          <div><span className="text-slate-400">Name:</span> {profile.fullName}</div>
          <div><span className="text-slate-400">Email:</span> {profile.email}</div>
          <div><span className="text-slate-400">Role:</span> {profile.role}</div>
        </div>
      </div>

      <form onSubmit={save} className="rounded-3xl border border-white/10 bg-white/5 p-6">
        <h3 className="text-2xl font-semibold text-white">Edit Profile</h3>
        <div className="mt-4 grid gap-4">
          <input name="fullName" value={form.fullName} onChange={onChange}
            className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none focus:border-indigo-400" />
          <input name="skills" value={form.skills} onChange={onChange}
            className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none focus:border-indigo-400" />
          <textarea name="profileSummary" value={form.profileSummary} onChange={onChange} rows="4"
            className="rounded-2xl border border-white/10 bg-slate-950/50 px-4 py-3 text-white outline-none focus:border-indigo-400" />
          <div className="flex items-center gap-4">
            <button className="rounded-2xl bg-indigo-500 px-5 py-3 font-semibold text-white transition hover:bg-indigo-400">Save Profile</button>
            {msg && <span className="text-sm text-slate-300">{msg}</span>}
          </div>
        </div>
      </form>
    </div>
  );
}

function Protected() {
  const token = localStorage.getItem("token");
  return token ? <Layout /> : <Navigate to="/" replace />;
}

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/app" element={<Protected />}>
        <Route index element={<Navigate to="/app/dashboard" replace />} />
        <Route path="dashboard" element={<DashboardPage />} />
        <Route path="projects" element={<ProjectsPage />} />
        <Route path="projects/new" element={<ProjectFormPage />} />
        <Route path="users" element={<UsersPage />} />
        <Route path="profile" element={<ProfilePage />} />
      </Route>
    </Routes>
  );
}
