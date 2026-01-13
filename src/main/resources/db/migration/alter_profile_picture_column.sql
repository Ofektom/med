-- Migration script to alter profile_picture column from VARCHAR(255) to TEXT
-- Run this script on your PostgreSQL database

ALTER TABLE users ALTER COLUMN profile_picture TYPE TEXT;

