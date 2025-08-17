import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import {Application} from "./Application.tsx";

// Import our custom CSS
import './scss/styles.scss'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Application />
  </StrictMode>,
)
