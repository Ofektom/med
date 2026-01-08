# Frontend Standardization Guide

This document outlines the standardization work completed and remaining tasks for unifying the design across all pages in the Hospital Management System.

## ✅ Completed Fixes

### 1. Broken Links Fixed
- ✅ `doc-patientlist.html`: Fixed `/doc-addprescription.html-editpatient.html` → `/doc-editpatient.html`
- ✅ `acc-patientlist.html`: Fixed `/acc-allassets.html-editpatient.html` → `/acc-editpatient.html`
- ✅ `acc-patientlist.html`: Fixed `/acc-allassets.html-addpatient.html` → `/acc-addpatient.html`
- ✅ `acc-paymentlist.html`: Fixed `/acc-allassets.html-allassets.html` → `/acc-allassets.html`
- ✅ `acc-paymentinvoice.html`: Fixed `/acc-allassets.html-allassets.html` → `/acc-allassets.html`
- ✅ `acc-patientlist.html`: Fixed home link from `/acc-allassets.html` → `/acc-dashboard.html`

### 2. Dashboard Standardization (Canonical Pages)
- ✅ **admin-dashboard.html**: 
  - Fixed accordion ID: `accordionExampleOne` → `accordionAdmin`
  - Fixed duplicate IDs: `headingSeven` → `headingAdminMobileNotification`, `headingAdminMobileMessages`, `headingAdminSettings`
  - Fixed dashboard title: "ADMIN Dashboard" → "Admin Dashboard"
  - Fixed "Setting" → "Settings"
  
- ✅ **doc-dashboard.html**:
  - Fixed accordion ID: `accordionExampleOne` → `accordionDoctor`
  - Fixed duplicate IDs with unique names
  - Fixed reset password link: `/resetpass.html` → `/doc-resetpass.html`
  - Fixed "Setting" → "Settings"

- ✅ **fd-dashboard.html**:
  - Fixed accordion ID: `accordionExampleOne` → `accordionFrontDesk`
  - Fixed duplicate IDs with unique names
  - Fixed profile link: `/fd-adminprofile.html` → `/fd-profile.html`
  - Fixed dashboard title: "FRONTDESK Dashboard" → "Front Desk Dashboard"
  - Fixed "Setting" → "Settings"

### 3. Head Section Standardization
- ✅ `pharm-dashboard.html`: Removed HTTrack comments, fixed Font Awesome CDN path

### 4. Backend Security
- ✅ Enabled `@EnableMethodSecurity(prePostEnabled = true)` in `WebSecurityConfig.java`
- ✅ Added import for `EnableMethodSecurity`

### 5. JavaScript Utilities
- ✅ Created `/assets/js/sidebar-utils.js` for active menu highlighting

---

## 📋 Remaining Standardization Tasks

### Phase 1: Head Section Standardization (All HTML Files)

**Files needing HTTrack comment removal (51 files):**
- All files listed in grep results need: Remove `<!-- Added by HTTrack -->` comments

**Files needing Font Awesome CDN fix (63 files):**
- Replace all instances of:
  - `../../../../cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css`
  - `/cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css`
  - `/assets/css/all.min.css` (if exists)
- With: `https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css`

**Standard Head Template:**
```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>EMR [Page Name]</title>
  <link rel="icon" type="image/x-icon" href="/includes/images/favicon.ico">
  <link rel="stylesheet" href="/assets/css/bootstrap-icons.css">
  <link rel="stylesheet" href="/assets/css/responsive.bootstrap5.min.css">
  <link rel="stylesheet" href="/assets/css/dataTables.bootstrap5.min.css">
  <link rel="stylesheet" href="/assets/css/bootstrap-datepicker.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
  <link rel="stylesheet" href="/assets/css/bootstrap.css">
  <link rel="stylesheet" href="/assets/css/style.css">
</head>
```

### Phase 2: Sidebar Standardization (All Role Pages)

#### Admin Pages (`admin-*.html`)
**Standard Sidebar Structure:**
- Accordion ID: `accordionAdmin`
- Dashboard title: "Admin Dashboard"
- Profile link: `/admin-profile.html`
- Reset password: `/admin-resetpass.html`
- Unique IDs: `headingAdminDoctor`, `collapseAdminDoctor`, `headingAdminSettings`, etc.

**Pages to update:** All `admin-*.html` files (except `admin-dashboard.html` which is canonical)

#### Doctor Pages (`doc-*.html`)
**Standard Sidebar Structure:**
- Accordion ID: `accordionDoctor`
- Dashboard title: "Doctor Dashboard"
- Profile link: `/doc-profile.html`
- Reset password: `/doc-resetpass.html`
- Unique IDs: `headingDocPrescription`, `collapseDocPrescription`, `headingDocSettings`, etc.

**Pages to update:** All `doc-*.html` files (except `doc-dashboard.html` which is canonical)

#### Front Desk Pages (`fd-*.html`)
**Standard Sidebar Structure:**
- Accordion ID: `accordionFrontDesk`
- Dashboard title: "Front Desk Dashboard"
- Profile link: `/fd-profile.html`
- Reset password: `/fd-resetpass.html`
- Includes Payments section (Payment List, Payment Invoice)
- Unique IDs: `headingFDPatient`, `collapseFDPatient`, `headingFDSettings`, etc.

**Pages to update:** All `fd-*.html` files (except `fd-dashboard.html` which is canonical)

#### Accountant Pages (`acc-*.html`)
**Standard Sidebar Structure:**
- Accordion ID: `accordionAccountant`
- Dashboard title: "Accountant Dashboard"
- Profile link: `/acc-profile.html`
- Reset password: `/acc-resetpass.html`
- Full accounting features access
- Unique IDs: `headingAccAssets`, `collapseAccAssets`, `headingAccSettings`, etc.

**Pages to update:** All `acc-*.html` files (except `acc-dashboard.html` which is canonical)

#### Pharmacist Pages (`pharm-*.html`)
**Standard Sidebar Structure:**
- Accordion ID: `accordionPharmacist`
- Dashboard title: "Pharmacist Dashboard"
- Profile link: `/pharm-profile.html`
- Reset password: `/pharm-resetpass.html`
- Unique IDs: `headingPharmMedication`, `collapsePharmMedication`, `headingPharmSettings`, etc.

**Pages to update:** All `pharm-*.html` files (except `pharm-dashboard.html` which is canonical)

#### Lab Scientist Pages (`lab-*.html`)
**Standard Sidebar Structure:**
- Accordion ID: `accordionLab`
- Dashboard title: "Lab Dashboard"
- Profile link: `/lab-profile.html`
- Reset password: `/lab-resetpass.html`
- Unique IDs: `headingLabSettings`, `collapseLabSettings`, etc.

**Pages to update:** All `lab-*.html` files (except `lab-dashboard.html` which is canonical)

### Phase 3: Profile/Reset Password Links Standardization

**Pattern:** `/{role}-profile.html` and `/{role}-resetpass.html`

**Files needing updates:**
- All pages with generic `/resetpass.html` → should be role-specific
- All pages with wrong profile links (e.g., `/fd-adminprofile.html`)

### Phase 4: Add Sidebar Utility Script

**Add to all HTML pages (before closing `</body>` tag):**
```html
<script src="/assets/js/sidebar-utils.js"></script>
```

This should be added after `script.js` and before any page-specific scripts.

### Phase 5: Header Standardization

**All pages should have consistent header structure:**
- Logo links to role dashboard (e.g., `/admin-dashboard.html`, `/doc-dashboard.html`)
- User dropdown links to role profile page
- Logout button functionality

---

## 🔧 Implementation Strategy

### Option 1: Manual Updates (Recommended for Quality Control)
1. Use the canonical dashboard for each role as the template
2. Copy the sidebar structure from the dashboard to all other pages of that role
3. Update accordion IDs to match role prefix
4. Fix all links to use role-specific paths
5. Add `sidebar-link` class to all menu links for active state management

### Option 2: Automated Script (For Bulk Updates)
Create a Python/Node.js script to:
1. Find and replace HTTrack comments
2. Fix Font Awesome CDN paths
3. Update accordion IDs based on file prefix
4. Fix profile/reset password links
5. Add sidebar-utils.js script reference

---

## 📝 Role-Based Feature Matrix

### Admin/Super Admin
- ✅ All features (full access)
- ✅ User management (all roles)
- ✅ Assets management
- ✅ Payments (full access)
- ✅ Reports
- ✅ Appointments
- ✅ Departments
- ✅ Wards

### Doctor
- ✅ Patients (view, edit)
- ✅ Appointments (view, book, edit)
- ✅ Prescriptions (view, add, edit)
- ✅ Reports
- ❌ Payments (no access)
- ❌ Assets (no access)
- ❌ User management (no access)

### Front Desk
- ✅ Patients (view, add, edit)
- ✅ Appointments (view, book, edit)
- ✅ Payments (Payment List, Payment Invoice) - Limited access
- ✅ Assets (view, add, edit)
- ✅ Staff (view)
- ✅ Reports
- ✅ Departments (view)
- ✅ Wards (view)
- ❌ Full accounting features (no access)

### Accountant
- ✅ Patients (view)
- ✅ Payments (full access - all accounting features)
- ✅ Assets (full access)
- ✅ Staff (view)
- ✅ Reports
- ✅ Departments (view)
- ✅ Wards (view)
- ❌ User management (no access)
- ❌ Appointments (no access)

### Pharmacist
- ✅ Patients (view)
- ✅ Medications (view, add, edit)
- ✅ Prescriptions (view)
- ✅ Reports
- ❌ Payments (no access)
- ❌ Appointments (no access)

### Lab Scientist
- ✅ Patients (view)
- ✅ Appointments (view)
- ✅ Reports
- ✅ Prescriptions (view)
- ❌ Payments (no access)
- ❌ Medications (no access)

---

## 🎯 Priority Order

1. **High Priority:**
   - Fix all broken links (✅ Done)
   - Standardize canonical dashboards (✅ Done)
   - Fix head sections (HTTrack, Font Awesome) - In Progress
   - Fix profile/reset password links

2. **Medium Priority:**
   - Standardize sidebars across all pages of each role
   - Add sidebar-utils.js to all pages
   - Fix duplicate accordion IDs

3. **Low Priority:**
   - Header standardization
   - Footer standardization
   - Breadcrumb navigation consistency

---

## 📌 Notes

- **Canonical Dashboards:** Use these as the source of truth for each role's sidebar structure
  - `admin-dashboard.html`
  - `doc-dashboard.html`
  - `fd-dashboard.html`
  - `acc-dashboard.html`
  - `pharm-dashboard.html`
  - `lab-dashboard.html`

- **Naming Conventions:**
  - Accordion IDs: `accordion{Role}` (e.g., `accordionAdmin`, `accordionDoctor`)
  - Heading IDs: `heading{Role}{Feature}` (e.g., `headingAdminDoctor`, `headingDocPrescription`)
  - Collapse IDs: `collapse{Role}{Feature}` (e.g., `collapseAdminDoctor`, `collapseDocPrescription`)

- **Link Patterns:**
  - Dashboard: `/{role}-dashboard.html`
  - Profile: `/{role}-profile.html`
  - Reset Password: `/{role}-resetpass.html`
  - Lists: `/{role}-{entity}list.html`
  - Add: `/{role}-add{entity}.html`
  - Edit: `/{role}-edit{entity}.html`

---

## ✅ Verification Checklist

After standardization, verify:
- [ ] All pages load without console errors
- [ ] All links work correctly
- [ ] Active menu items highlight correctly
- [ ] Sidebars are consistent within each role
- [ ] No duplicate IDs in any page
- [ ] All Font Awesome icons load correctly
- [ ] Profile/reset password links are role-specific
- [ ] Dashboard titles are in Title Case
- [ ] Backend security properly enforces role-based access

---

**Last Updated:** [Current Date]
**Status:** In Progress - Core fixes completed, bulk standardization pending

