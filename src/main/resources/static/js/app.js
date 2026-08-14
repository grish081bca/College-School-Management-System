document.querySelectorAll('form[action*="/delete"]').forEach(form => form.addEventListener('submit', event => {
    if (!confirm('Delete this record?')) {
        event.preventDefault();
    }
}));

const dateTime = document.getElementById('currentDateTime');
if (dateTime) {
    const updateDateTime = () => {
        dateTime.textContent = new Date().toLocaleString();
    };
    updateDateTime();
    setInterval(updateDateTime, 1000);
}

document.querySelectorAll('.profile-trigger').forEach(trigger => {
    trigger.addEventListener('click', event => {
        event.stopPropagation();
        const menu = trigger.closest('.profile-menu');
        const isOpen = menu.classList.toggle('open');
        trigger.setAttribute('aria-expanded', String(isOpen));
    });
});

document.querySelectorAll('.menu-toggle').forEach(trigger => {
    trigger.addEventListener('click', (e) => {
        const group = trigger.closest('.menu-group');
        if (!group) return;
        const isCollapsed = appShell && appShell.classList.contains('sidebar-collapsed');
        if (isCollapsed) {
            appShell.classList.remove('sidebar-collapsed');
            localStorage.setItem('erp.sidebarCollapsed', 'false');
            document.querySelectorAll('.sidebar-toggle').forEach(t => t.setAttribute('aria-expanded', 'true'));
        }
        group.classList.toggle('open');
        if (e.target && e.target.tagName === 'BUTTON') e.preventDefault();
    });
});

const appShell = document.getElementById('appShell');
const sidebarToggle = document.querySelector('.sidebar-toggle');
if (appShell && sidebarToggle) {
    const storedState = localStorage.getItem('erp.sidebarCollapsed');
    if (storedState === 'true') {
        appShell.classList.add('sidebar-collapsed');
        sidebarToggle.setAttribute('aria-expanded', 'false');
        document.querySelectorAll('.menu-group.open').forEach(g => g.classList.remove('open'));
    }
    sidebarToggle.addEventListener('click', event => {
        event.stopPropagation();
        const collapsed = appShell.classList.toggle('sidebar-collapsed');
        localStorage.setItem('erp.sidebarCollapsed', String(collapsed));
        sidebarToggle.setAttribute('aria-expanded', String(!collapsed));
        if (collapsed) {
            document.querySelectorAll('.menu-group.open').forEach(g => g.classList.remove('open'));
        }
    });

    document.querySelectorAll('.super-menu').forEach(el => {
        el.addEventListener('click', function(e) {
            if (appShell.classList.contains('sidebar-collapsed')) {
                if (e.target && (e.target.tagName === 'A' || e.target.closest('a'))) {
                    e.preventDefault();
                }
                appShell.classList.remove('sidebar-collapsed');
                localStorage.setItem('erp.sidebarCollapsed', 'false');
                document.querySelectorAll('.sidebar-toggle').forEach(t => t.setAttribute('aria-expanded', 'true'));
                const group = el.closest('.menu-group');
                if (group) {
                    document.querySelectorAll('.menu-group.open').forEach(g => g.classList.remove('open'));
                    group.classList.add('open');
                }
            }
        });
    });
}

document.addEventListener('click', () => {
    document.querySelectorAll('.profile-menu.open').forEach(menu => {
        menu.classList.remove('open');
        const trigger = menu.querySelector('.profile-trigger');
        if (trigger) {
            trigger.setAttribute('aria-expanded', 'false');
        }
    });
});
