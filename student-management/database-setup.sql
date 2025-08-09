-- Student Management System Database Setup
-- PostgreSQL Database Creation and Configuration

-- Create database (if not exists)
-- Note: Run this as superuser if database doesn't exist
-- CREATE DATABASE airodb;

-- Connect to the database
\c airodb;

-- Create students table
CREATE TABLE IF NOT EXISTS students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    course VARCHAR(100) NOT NULL,
    fee DECIMAL(10,2) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15) UNIQUE,
    address VARCHAR(200),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_students_name ON students(name);
CREATE INDEX IF NOT EXISTS idx_students_course ON students(course);
CREATE INDEX IF NOT EXISTS idx_students_status ON students(status);
CREATE INDEX IF NOT EXISTS idx_students_email ON students(email);
CREATE INDEX IF NOT EXISTS idx_students_phone ON students(phone);

-- Insert sample data (only if table is empty)
INSERT INTO students (name, course, fee, email, phone, address, status) 
SELECT * FROM (VALUES
    ('John Doe', 'Computer Science', 1500.00, 'john.doe@email.com', '+1234567890', '123 Main St, City, State', 'ACTIVE'),
    ('Jane Smith', 'Mathematics', 1200.00, 'jane.smith@email.com', '+1234567891', '456 Oak Ave, City, State', 'ACTIVE'),
    ('Mike Johnson', 'Physics', 1300.00, 'mike.johnson@email.com', '+1234567892', '789 Pine Rd, City, State', 'ACTIVE'),
    ('Sarah Wilson', 'Chemistry', 1400.00, 'sarah.wilson@email.com', '+1234567893', '321 Elm St, City, State', 'ACTIVE'),
    ('David Brown', 'Biology', 1250.00, 'david.brown@email.com', '+1234567894', '654 Maple Dr, City, State', 'ACTIVE'),
    ('Lisa Davis', 'Computer Science', 1500.00, 'lisa.davis@email.com', '+1234567895', '987 Cedar Ln, City, State', 'ACTIVE'),
    ('Tom Miller', 'Engineering', 1600.00, 'tom.miller@email.com', '+1234567896', '147 Birch Way, City, State', 'ACTIVE'),
    ('Amy Garcia', 'Psychology', 1100.00, 'amy.garcia@email.com', '+1234567897', '258 Spruce Ct, City, State', 'ACTIVE'),
    ('Chris Rodriguez', 'Economics', 1350.00, 'chris.rodriguez@email.com', '+1234567898', '369 Willow Pl, City, State', 'ACTIVE'),
    ('Emma Taylor', 'English Literature', 1000.00, 'emma.taylor@email.com', '+1234567899', '741 Aspen Cir, City, State', 'ACTIVE')
) AS v(name, course, fee, email, phone, address, status)
WHERE NOT EXISTS (SELECT 1 FROM students LIMIT 1);

-- Create a function to update the updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create trigger to automatically update updated_at
DROP TRIGGER IF EXISTS update_students_updated_at ON students;
CREATE TRIGGER update_students_updated_at 
    BEFORE UPDATE ON students 
    FOR EACH ROW 
    EXECUTE FUNCTION update_updated_at_column();

-- Grant permissions to airouser
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO airouser;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO airouser;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO airouser;

-- Display the created data
SELECT * FROM students ORDER BY id;
