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
    trigger.addEventListener('click', () => {
        trigger.closest('.menu-group').classList.toggle('open');
    });
});

const appShell = document.getElementById('appShell');
const sidebarToggle = document.querySelector('.sidebar-toggle');
if (appShell && sidebarToggle) {
    const storedState = localStorage.getItem('erp.sidebarCollapsed');
    if (storedState === 'true') {
        appShell.classList.add('sidebar-collapsed');
        sidebarToggle.setAttribute('aria-expanded', 'false');
    }
    sidebarToggle.addEventListener('click', event => {
        event.stopPropagation();
        const collapsed = appShell.classList.toggle('sidebar-collapsed');
        localStorage.setItem('erp.sidebarCollapsed', String(collapsed));
        sidebarToggle.setAttribute('aria-expanded', String(!collapsed));
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
