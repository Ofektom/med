/**
 * Sidebar Management Utility
 * Handles active menu highlighting and sidebar state management
 */

(function() {
    'use strict';

    /**
     * Highlight active menu item based on current page
     */
    function highlightActiveMenuItem() {
        const currentPath = window.location.pathname;
        const currentPage = currentPath.split('/').pop() || 'index.html';

        // Remove all active classes
        document.querySelectorAll('.sidebar-link, .nav-link').forEach(link => {
            link.classList.remove('active');
        });

        // Find and highlight the active link
        document.querySelectorAll('.sidebar-link, .nav-link').forEach(link => {
            const href = link.getAttribute('href');
            if (href && (href === currentPath || href.endsWith(currentPage))) {
                link.classList.add('active');
                
                // If it's inside an accordion, expand the parent
                const accordionItem = link.closest('.accordion-item');
                if (accordionItem) {
                    const collapse = accordionItem.querySelector('.accordion-collapse');
                    const button = accordionItem.querySelector('.accordion-button');
                    if (collapse && button && !button.classList.contains('collapsed')) {
                        button.classList.remove('collapsed');
                        collapse.classList.add('show');
                    }
                }
            }
        });
    }

    /**
     * Initialize sidebar functionality
     */
    function initSidebar() {
        // Highlight active menu on page load
        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', highlightActiveMenuItem);
        } else {
            highlightActiveMenuItem();
        }

        // Re-highlight on navigation (for SPA-like behavior if implemented)
        window.addEventListener('popstate', highlightActiveMenuItem);
    }

    // Initialize when script loads
    initSidebar();

    // Export for manual calls if needed
    window.SidebarUtils = {
        highlightActive: highlightActiveMenuItem,
        init: initSidebar
    };
})();

