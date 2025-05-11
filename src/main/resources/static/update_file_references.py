import os
import re
from pathlib import Path

# Configuration
SOURCE_DIR = "."
BACKUP_SUFFIX = ".bak"
OLD_PREFIX = "lucidoutsourcing.com/demo/healthcare/ltprw/"
NEW_PREFIX = "/"

def update_file_references(file_path):
    """Update file references in the given HTML file."""
    with open(file_path, 'r', encoding='utf-8') as file:
        content = file.read()

    patterns = [
        r'(href|src)="{}([^"]*)"'.format(re.escape(OLD_PREFIX)),
        r'(url\()({}[^)]+\))'.format(re.escape(OLD_PREFIX))
    ]

    changes_made = False
    new_content = content

    for pattern in patterns:
        if re.search(pattern, new_content):
            changes_made = True
            new_content = re.sub(pattern, r'\1="{}"'.format(r'\2'.replace(OLD_PREFIX, NEW_PREFIX)), new_content)

    if changes_made:
        backup_path = file_path + BACKUP_SUFFIX
        with open(backup_path, 'w', encoding='utf-8') as backup_file:
            backup_file.write(content)
        print(f"Created backup: {backup_path}")

        with open(file_path, 'w', encoding='utf-8') as file:
            file.write(new_content)
        print(f"Updated: {file_path}")
    else:
        print(f"No changes needed: {file_path}")

def process_directory(directory):
    """Process all HTML files in the specified directory and its subdirectories."""
    for root, _, files in os.walk(directory):
        for file in files:
            if file.lower().endswith('.html'):
                file_path = os.path.join(root, file)
                update_file_references(file_path)

if __name__ == "__main__":
    source_path = Path(SOURCE_DIR)
    if not source_path.exists():
        print(f"Error: Directory '{SOURCE_DIR}' does not exist.")
        exit(1)

    print(f"Processing HTML files in {SOURCE_DIR}...")
    process_directory(SOURCE_DIR)
    print("File reference update completed!")