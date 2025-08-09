const API = '/api/students';

const dom = {
  body: document.getElementById('students-body'),
  form: document.getElementById('student-form'),
  id: document.getElementById('student-id'),
  firstName: document.getElementById('firstName'),
  lastName: document.getElementById('lastName'),
  email: document.getElementById('email'),
  dob: document.getElementById('dateOfBirth'),
  msg: document.getElementById('message'),
  resetBtn: document.getElementById('reset-btn'),
  formTitle: document.getElementById('form-title'),
  search: document.getElementById('search'),
};

let allStudents = [];

function showMessage(text, type = 'info') {
  dom.msg.style.color = type === 'error' ? '#ef4444' : '#9ca3af';
  dom.msg.textContent = text || '';
}

function clearForm() {
  dom.id.value = '';
  dom.firstName.value = '';
  dom.lastName.value = '';
  dom.email.value = '';
  dom.dob.value = '';
  dom.formTitle.textContent = 'Add Student';
  showMessage('');
}

function render(students) {
  dom.body.innerHTML = '';
  students.forEach((s, i) => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${i + 1}</td>
      <td>${s.firstName}</td>
      <td>${s.lastName}</td>
      <td>${s.email}</td>
      <td>${s.dateOfBirth || ''}</td>
      <td class="actions-cell">
        <button class="btn" data-action="edit" data-id="${s.id}">Edit</button>
        <button class="btn danger" data-action="delete" data-id="${s.id}">Delete</button>
      </td>
    `;
    dom.body.appendChild(tr);
  });
}

async function loadStudents() {
  try {
    const res = await fetch(API);
    if (!res.ok) throw new Error('Failed to load students');
    allStudents = await res.json();
    render(allStudents);
  } catch (e) {
    showMessage(e.message, 'error');
  }
}

dom.body.addEventListener('click', async (e) => {
  const btn = e.target.closest('button');
  if (!btn) return;
  const id = btn.dataset.id;
  const action = btn.dataset.action;

  if (action === 'edit') {
    try {
      const res = await fetch(`${API}/${id}`);
      if (!res.ok) throw new Error('Failed to load student');
      const s = await res.json();
      dom.id.value = s.id;
      dom.firstName.value = s.firstName;
      dom.lastName.value = s.lastName;
      dom.email.value = s.email;
      dom.dob.value = s.dateOfBirth || '';
      dom.formTitle.textContent = 'Edit Student';
      showMessage('Editing student...');
    } catch (e) {
      showMessage(e.message, 'error');
    }
  }

  if (action === 'delete') {
    if (!confirm('Delete this student?')) return;
    try {
      const res = await fetch(`${API}/${id}`, { method: 'DELETE' });
      if (!res.ok) throw new Error('Delete failed');
      await loadStudents();
      if (dom.id.value === id) clearForm();
      showMessage('Deleted');
    } catch (e) {
      showMessage(e.message, 'error');
    }
  }
});

dom.form.addEventListener('submit', async (e) => {
  e.preventDefault();
  const payload = {
    firstName: dom.firstName.value.trim(),
    lastName: dom.lastName.value.trim(),
    email: dom.email.value.trim(),
    dateOfBirth: dom.dob.value || null,
  };

  const id = dom.id.value;
  const method = id ? 'PUT' : 'POST';
  const url = id ? `${API}/${id}` : API;

  try {
    const res = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    if (!res.ok) {
      const err = await res.json().catch(() => ({}));
      const msg = err.error || (err.errors ? Object.values(err.errors).join(', ') : 'Request failed');
      throw new Error(msg);
    }

    await loadStudents();
    clearForm();
    showMessage('Saved');
  } catch (e) {
    showMessage(e.message, 'error');
  }
});

dom.resetBtn.addEventListener('click', clearForm);

dom.search.addEventListener('input', (e) => {
  const q = e.target.value.toLowerCase();
  const filtered = allStudents.filter(s =>
    [s.firstName, s.lastName, s.email].some(v => (v || '').toLowerCase().includes(q))
  );
  render(filtered);
});

loadStudents();


