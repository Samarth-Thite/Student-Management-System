interface Student {
  id?: number;
  name: string;
  course: string;
  fee: number;
}

const API_URL = 'http://localhost:8080/students'; // Update this to match your Spring Boot endpoint

const form = document.getElementById('studentForm') as HTMLFormElement;
const tableBody = document.getElementById('studentTableBody') as HTMLTableSectionElement;

form.onsubmit = async (e) => {
  e.preventDefault();
  const id = (document.getElementById('studentId') as HTMLInputElement).value;
  const name = (document.getElementById('name') as HTMLInputElement).value;
  const course = (document.getElementById('course') as HTMLInputElement).value;
  const fee = parseFloat((document.getElementById('fee') as HTMLInputElement).value);

  const student: Student = { name, course, fee };

  if (id) {
    await fetch(`${API_URL}/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(student),
    });
  } else {
    await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(student),
    });
  }

  form.reset();
  loadStudents();
};

async function loadStudents() {
  const res = await fetch(API_URL);
  const students: Student[] = await res.json();

  tableBody.innerHTML = '';
  students.forEach((student) => {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${student.id}</td>
      <td>${student.name}</td>
      <td>${student.course}</td>
      <td>${student.fee}</td>
      <td>
        <button onclick='editStudent(${JSON.stringify(student)})'>✏️</button>
        <button onclick='deleteStudent(${student.id})'>🗑️</button>
      </td>
    `;
    tableBody.appendChild(row);
  });
}

(window as any).editStudent = (student: Student) => {
  (document.getElementById('studentId') as HTMLInputElement).value = String(student.id || '');
  (document.getElementById('name') as HTMLInputElement).value = student.name;
  (document.getElementById('course') as HTMLInputElement).value = student.course;
  (document.getElementById('fee') as HTMLInputElement).value = String(student.fee);
};

(window as any).deleteStudent = async (id: number) => {
  await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
  loadStudents();
};

loadStudents();
