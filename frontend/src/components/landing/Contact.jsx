import React from 'react';

const Contact = () => {
  return (
    <section id="contact" className="py-20 bg-gray-900 text-white relative">
      <div className="max-w-4xl mx-auto px-4 relative z-10">
        <div className="text-center mb-16">
          <h2 className="text-4xl font-bold mb-4 bg-clip-text text-transparent bg-gradient-to-r from-emerald-400 to-indigo-400">Get In Touch</h2>
          <p className="text-gray-400 text-lg">Have questions? We'd love to hear from you.</p>
        </div>

        <div className="bg-gray-950 border border-gray-800 rounded-3xl p-8 md:p-12 shadow-2xl">
          <form className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label className="block text-sm font-medium text-gray-400 mb-2">First Name</label>
                <input
                  type="text"
                  className="w-full bg-gray-900 border border-gray-800 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-indigo-500 outline-none transition-all"
                  placeholder="John"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-400 mb-2">Last Name</label>
                <input
                  type="text"
                  className="w-full bg-gray-900 border border-gray-800 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-indigo-500 outline-none transition-all"
                  placeholder="Doe"
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-400 mb-2">Email Address</label>
              <input
                type="email"
                className="w-full bg-gray-900 border border-gray-800 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-indigo-500 outline-none transition-all"
                placeholder="john@example.com"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-400 mb-2">Message</label>
              <textarea
                rows="4"
                className="w-full bg-gray-900 border border-gray-800 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-indigo-500 outline-none transition-all resize-none"
                placeholder="How can we help you?"
              ></textarea>
            </div>

            <button
              type="button"
              className="w-full bg-indigo-600 hover:bg-indigo-500 text-white font-bold rounded-xl px-4 py-4 transition-all shadow-lg shadow-indigo-600/30"
            >
              Send Message
            </button>
          </form>
        </div>
      </div>
    </section>
  );
};

export default Contact;
