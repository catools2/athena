import type { Config } from "tailwindcss";

/**
 * Athena's palette, lifted from the tokens already in src/styles/main.css so the Tailwind
 * pages and the not-yet-migrated CSS pages agree while both exist. Indigo-violet on near-black,
 * deliberately distinct from the reference app's blue.
 */
export default {
  content: ["./index.html", "./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        ink: {
          DEFAULT: "#f5f7fb",
          muted: "#aeb6c6",
        },
        surface: {
          base: "#111216",
          DEFAULT: "rgb(28 30 38 / <alpha-value>)",
          strong: "rgb(35 37 46 / <alpha-value>)",
          muted: "rgb(20 22 29 / <alpha-value>)",
        },
        accent: {
          DEFAULT: "#5b7cff",
          strong: "#7f66ff",
          violet: "#c26dff",
          cyan: "#29d0ff",
          warm: "#ffb248",
          coral: "#ff7b81",
        },
        state: {
          success: "#44d4a8",
          warning: "#ffbe5c",
          danger: "#ff7b81",
        },
        line: {
          DEFAULT: "rgb(255 255 255 / 0.08)",
          strong: "rgb(255 255 255 / 0.14)",
        },
      },
      fontFamily: {
        sans: ["IBM Plex Sans", "system-ui", "sans-serif"],
        display: ["Manrope", "IBM Plex Sans", "system-ui", "sans-serif"],
      },
      borderRadius: {
        md: "14px",
        lg: "20px",
        xl: "28px",
      },
      boxShadow: {
        panel: "0 18px 42px rgba(0, 0, 0, 0.2)",
        raised: "0 34px 90px rgba(0, 0, 0, 0.28)",
      },
      keyframes: {
        fadeIn: { "0%": { opacity: "0" }, "100%": { opacity: "1" } },
      },
      animation: { "fade-in": "fadeIn 0.25s ease-in-out" },
    },
  },
  plugins: [],
  // Preflight is off deliberately. It is a global element reset, and main.css already
  // styles those elements for the pages that have not migrated yet - turning it on would
  // restyle every existing page the moment Tailwind is imported. It can be enabled once
  // nothing depends on main.css.
  corePlugins: { preflight: false },
} satisfies Config;
