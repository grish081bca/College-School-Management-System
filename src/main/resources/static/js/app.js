document.querySelectorAll('form[action*="/delete"]').forEach(form=>form.addEventListener('submit',event=>{if(!confirm('Delete this record?'))event.preventDefault();}));
