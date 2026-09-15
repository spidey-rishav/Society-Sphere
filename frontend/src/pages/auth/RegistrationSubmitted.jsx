import { Link, useLocation } from 'react-router-dom';

export default function RegistrationSubmitted() {
  const { state } = useLocation();
  return (
    <main className="min-h-screen bg-gray-950 flex items-center justify-center p-6 text-white">
      <section className="w-full max-w-lg rounded-3xl border border-emerald-500/30 bg-gray-900 p-8 text-center shadow-2xl">
        <div className="mx-auto mb-5 flex h-16 w-16 items-center justify-center rounded-full bg-emerald-500/15 text-3xl text-emerald-400">✓</div>
        <h1 className="text-3xl font-bold">Registration submitted</h1>
        <p className="mt-3 text-gray-300">Your society registration request is now awaiting super-admin review.</p>
        {state?.societyCode && <p className="mt-5 rounded-xl bg-gray-800 px-4 py-3 text-sm text-gray-300">Request reference: <strong className="text-white">{state.societyCode}</strong></p>}
        <p className="mt-5 text-sm text-gray-400">Once approved, credentials will be sent to the administrator email you provided.</p>
        <Link to="/society" className="mt-8 inline-flex rounded-xl bg-emerald-600 px-5 py-3 font-semibold hover:bg-emerald-500">Back to society options</Link>
      </section>
    </main>
  );
}
