-- init.sql

-- Create the productcatalog schema
CREATE SCHEMA IF NOT EXISTS productcatalog;

-- Set the search_path to include the productcatalog schema
SET search_path TO productcatalog, public;
