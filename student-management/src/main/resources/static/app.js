// Student Management System - Frontend JavaScript

// API Base URL
const API_BASE_URL = '/api/students';

// Global variables
let currentPage = 0;
let currentPageSize = 10;
let currentSortBy = 'id';
let currentSortDir = 'asc';

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    loadStudents();
    setupEventListeners();
    loadCourseOptions();
});

// Setup event listeners
function setupEventListeners() {
    // Form submission
    document.getElementById('studentForm').addEventListener('submit', function(e) {
        e.preventDefault();
        createStudent();
    });

    // Search input
    document.getElementById('searchInput').addEventListener('input', debounce(function() {
        loadStudents();
    }, 500));

    // Filters
    document.getElementById('courseFilter').addEventListener('change', loadStudents);
    document.getElementById('statusFilter').addEventListener('change', loadStudents);
    document.getElementById('pageSize').addEventListener('change', function() {
        currentPageSize = parseInt(this.value);
        currentPage = 0;
        loadStudents();
    });
}

// Debounce function for search
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Load students with pagination and filters
async function loadStudents() {
    try {
        showLoading(true);
        
        const searchName = document.getElementById('searchInput').value;
        const courseFilter = document.getElementById('courseFilter').value;
        const statusFilter = document.getElementById('statusFilter').value;
        
        let url = `${API_BASE_URL}?page=${currentPage}&size=${currentPageSize}&sortBy=${currentSortBy}&sortDir=${currentSortDir}`;
        
        if (searchName || courseFilter || statusFilter) {
            url = `${API_BASE_URL}/search?page=${currentPage}&size=${currentPageSize}`;
            if (searchName) url += `&name=${encodeURIComponent(searchName)}`;
            if (courseFilter) url += `&course=${encodeURIComponent(courseFilter)}`;
            if (statusFilter) url += `&status=${encodeURIComponent(statusFilter)}`;
        }
        
        console.log('Fetching URL:', url); // Debug log
        
        const response = await fetch(url);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const result = await response.json();
        
        if (result.success) {
            displayStudents(result.data.content || result.data);
            if (result.data.totalPages !== undefined) {
                displayPagination(result.data);
            }
        } else {
            showNotification('Error loading students: ' + result.message, 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Error loading students: ' + error.message, 'error');
    } finally {
        showLoading(false);
    }
}

// Display students in the table
function displayStudents(students) {
    const tbody = document.getElementById('studentsTableBody');
    tbody.innerHTML = '';
    
    if (students.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" class="text-center">No students found</td></tr>';
        return;
    }
    
    students.forEach(student => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.course}</td>
            <td>$${student.fee?.toFixed(2) || '0.00'}</td>
            <td>${student.email || '-'}</td>
            <td>${student.phone || '-'}</td>
            <td><span class="status-badge status-${student.status?.toLowerCase() || 'active'}">${student.status || 'ACTIVE'}</span></td>
            <td>
                <button class="btn btn-sm btn-outline-primary action-btn" onclick="editStudent(${student.id})" title="Edit">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger action-btn" onclick="deleteStudent(${student.id})" title="Delete">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// Display pagination
function displayPagination(pageData) {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = '';
    
    if (pageData.totalPages <= 1) return;
    
    // Previous button
    const prevLi = document.createElement('li');
    prevLi.className = `page-item ${pageData.first ? 'disabled' : ''}`;
    prevLi.innerHTML = `<a class="page-link" href="#" onclick="changePage(${currentPage - 1})">Previous</a>`;
    pagination.appendChild(prevLi);
    
    // Page numbers
    const startPage = Math.max(0, currentPage - 2);
    const endPage = Math.min(pageData.totalPages - 1, currentPage + 2);
    
    for (let i = startPage; i <= endPage; i++) {
        const li = document.createElement('li');
        li.className = `page-item ${i === currentPage ? 'active' : ''}`;
        li.innerHTML = `<a class="page-link" href="#" onclick="changePage(${i})">${i + 1}</a>`;
        pagination.appendChild(li);
    }
    
    // Next button
    const nextLi = document.createElement('li');
    nextLi.className = `page-item ${pageData.last ? 'disabled' : ''}`;
    nextLi.innerHTML = `<a class="page-link" href="#" onclick="changePage(${currentPage + 1})">Next</a>`;
    pagination.appendChild(nextLi);
}

// Change page
function changePage(page) {
    currentPage = page;
    loadStudents();
}

// Create new student
async function createStudent() {
    try {
        const formData = getFormData();
        console.log('Creating student with data:', formData); // Debug log
        
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        });
        
        console.log('Response status:', response.status); // Debug log
        
        if (!response.ok) {
            const errorText = await response.text();
            console.error('Server response:', errorText); // Debug log
            throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
        }
        
        const result = await response.json();
        console.log('Response result:', result); // Debug log
        
        if (result.success) {
            showNotification('Student created successfully!', 'success');
            document.getElementById('studentForm').reset();
            loadStudents();
            loadCourseOptions();
        } else {
            showNotification('Error creating student: ' + result.message, 'error');
        }
    } catch (error) {
        console.error('Error creating student:', error);
        showNotification('Error creating student: ' + error.message, 'error');
    }
}

// Edit student
async function editStudent(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`);
        const result = await response.json();
        
        if (result.success) {
            const student = result.data;
            populateEditForm(student);
            new bootstrap.Modal(document.getElementById('editModal')).show();
        } else {
            showNotification('Error loading student: ' + result.message, 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Error loading student', 'error');
    }
}

// Update student
async function updateStudent() {
    try {
        const id = document.getElementById('editId').value;
        const formData = getEditFormData();
        
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        });
        
        const result = await response.json();
        
        if (result.success) {
            showNotification('Student updated successfully!', 'success');
            bootstrap.Modal.getInstance(document.getElementById('editModal')).hide();
            loadStudents();
        } else {
            showNotification('Error updating student: ' + result.message, 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Error updating student', 'error');
    }
}

// Delete student
async function deleteStudent(id) {
    if (!confirm('Are you sure you want to delete this student?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE'
        });
        
        const result = await response.json();
        
        if (result.success) {
            showNotification('Student deleted successfully!', 'success');
            loadStudents();
        } else {
            showNotification('Error deleting student: ' + result.message, 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Error deleting student', 'error');
    }
}

// Show statistics
async function showStatistics() {
    try {
        const response = await fetch(`${API_BASE_URL}/statistics`);
        const result = await response.json();
        
        if (result.success) {
            const stats = result.data;
            const content = document.getElementById('statisticsContent');
            content.innerHTML = `
                <div class="col-md-6">
                    <div class="stat-card">
                        <h3>${stats.totalStudents}</h3>
                        <p>Total Students</p>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="stat-card">
                        <h3>${stats.activeStudents}</h3>
                        <p>Active Students</p>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="stat-card">
                        <h3>$${stats.totalFee?.toFixed(2) || '0.00'}</h3>
                        <p>Total Fees Collected</p>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="stat-card">
                        <h3>$${stats.averageFee?.toFixed(2) || '0.00'}</h3>
                        <p>Average Fee</p>
                    </div>
                </div>
            `;
            new bootstrap.Modal(document.getElementById('statisticsModal')).show();
        } else {
            showNotification('Error loading statistics: ' + result.message, 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Error loading statistics', 'error');
    }
}

// Load course options for filter
async function loadCourseOptions() {
    try {
        const response = await fetch(`${API_BASE_URL}/all`);
        const result = await response.json();
        
        if (result.success) {
            const courses = [...new Set(result.data.map(student => student.course))];
            const courseFilter = document.getElementById('courseFilter');
            courseFilter.innerHTML = '<option value="">All Courses</option>';
            
            courses.forEach(course => {
                const option = document.createElement('option');
                option.value = course;
                option.textContent = course;
                courseFilter.appendChild(option);
            });
        }
    } catch (error) {
        console.error('Error loading courses:', error);
    }
}

// Helper functions
function getFormData() {
    return {
        name: document.getElementById('name').value,
        course: document.getElementById('course').value,
        fee: parseFloat(document.getElementById('fee').value),
        email: document.getElementById('email').value || null,
        phone: document.getElementById('phone').value || null,
        address: document.getElementById('address').value || null,
        status: document.getElementById('status').value
    };
}

function getEditFormData() {
    return {
        name: document.getElementById('editName').value,
        course: document.getElementById('editCourse').value,
        fee: parseFloat(document.getElementById('editFee').value),
        email: document.getElementById('editEmail').value || null,
        phone: document.getElementById('editPhone').value || null,
        address: document.getElementById('editAddress').value || null,
        status: document.getElementById('editStatus').value
    };
}

function populateEditForm(student) {
    document.getElementById('editId').value = student.id;
    document.getElementById('editName').value = student.name;
    document.getElementById('editCourse').value = student.course;
    document.getElementById('editFee').value = student.fee;
    document.getElementById('editEmail').value = student.email || '';
    document.getElementById('editPhone').value = student.phone || '';
    document.getElementById('editAddress').value = student.address || '';
    document.getElementById('editStatus').value = student.status || 'ACTIVE';
}

function showLoading(show) {
    const buttons = document.querySelectorAll('button');
    buttons.forEach(button => {
        if (show) {
            button.disabled = true;
        } else {
            button.disabled = false;
        }
    });
}

function showNotification(message, type = 'info') {
    const toast = document.getElementById('notificationToast');
    const title = document.getElementById('toastTitle');
    const messageEl = document.getElementById('toastMessage');
    
    title.textContent = type.charAt(0).toUpperCase() + type.slice(1);
    messageEl.textContent = message;
    
    // Set toast color based on type
    toast.className = `toast ${type === 'success' ? 'bg-success text-white' : type === 'error' ? 'bg-danger text-white' : ''}`;
    
    const bsToast = new bootstrap.Toast(toast);
    bsToast.show();
}
