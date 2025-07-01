#!/usr/bin/env python3
import re
import sys
import os

def fix_brackets_in_file(file_path):
    """Fix missing closing parentheses in shouldBe calls"""
    with open(file_path, 'r') as f:
        content = f.read()
    
    # Fix missing closing parentheses in shouldBe calls
    # Pattern: .shouldBe(something  without closing )
    pattern = r'\.shouldBe\(([^)]+)$'
    
    def replace_bracket(match):
        inner = match.group(1).strip()
        return f'.shouldBe({inner})'
    
    # Split into lines and process each one
    lines = content.split('\n')
    fixed_lines = []
    
    for line in lines:
        # Check if line contains shouldBe( but doesn't end with )
        if '.shouldBe(' in line and not line.strip().endswith(')'):
            # Find where shouldBe( starts
            shouldbe_index = line.find('.shouldBe(')
            if shouldbe_index != -1:
                prefix = line[:shouldbe_index]
                shouldbe_part = line[shouldbe_index+10:]  # Skip '.shouldBe('
                # Add closing parenthesis
                fixed_line = prefix + '.shouldBe(' + shouldbe_part + ')'
                fixed_lines.append(fixed_line)
            else:
                fixed_lines.append(line)
        else:
            fixed_lines.append(line)
    
    # Join back
    new_content = '\n'.join(fixed_lines)
    
    # Write back
    with open(file_path, 'w') as f:
        f.write(new_content)

def main():
    # Find all Spec.scala files
    spec_files = []
    for root, dirs, files in os.walk('/home/kota_ubuntu/Projects/FormalCalculation'):
        for file in files:
            if file.endswith('Spec.scala'):
                spec_files.append(os.path.join(root, file))
    
    print(f"Found {len(spec_files)} spec files")
    for file_path in spec_files:
        print(f"Fixing brackets in {file_path}")
        fix_brackets_in_file(file_path)
    
    print("Done!")

if __name__ == '__main__':
    main()