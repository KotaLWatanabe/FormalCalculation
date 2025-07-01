#!/usr/bin/env python3
import re
import sys
import os

def fix_syntax_issues(file_path):
    """Fix remaining syntax issues in test files"""
    with open(file_path, 'r') as f:
        content = f.read()
    
    # Fix shouldBe( without closing )
    content = re.sub(r'\.shouldBe\(([^)]+)$', r'.shouldBe(\1)', content, flags=re.MULTILINE)
    
    # Fix comment syntax issues - remove ) before //
    content = re.sub(r'\)(\s*//)', r'\1', content)
    
    # Fix cases where shouldBe( appears but needs closing )
    lines = content.split('\n')
    fixed_lines = []
    
    for line in lines:
        # If line has shouldBe( and doesn't end with ), add it
        if '.shouldBe(' in line and not line.strip().endswith(')') and '//' not in line:
            # Add closing parenthesis before comment or end of line
            if '//' in line:
                # Split at comment
                parts = line.split('//', 1)
                code_part = parts[0].strip()
                comment_part = parts[1] if len(parts) > 1 else ''
                if not code_part.endswith(')'):
                    code_part += ')'
                fixed_line = code_part + ' // ' + comment_part if comment_part else code_part
                fixed_lines.append(fixed_line)
            else:
                # No comment, just add closing parenthesis
                if not line.strip().endswith(')'):
                    fixed_lines.append(line + ')')
                else:
                    fixed_lines.append(line)
        else:
            fixed_lines.append(line)
    
    new_content = '\n'.join(fixed_lines)
    
    # Additional fixes for specific syntax issues
    # Fix missing parentheses in specific cases
    new_content = re.sub(r'\.shouldBe\((false)$', r'.shouldBe(\1)', new_content, flags=re.MULTILINE)
    new_content = re.sub(r'\.shouldBe\((true)$', r'.shouldBe(\1)', new_content, flags=re.MULTILINE)
    
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
        print(f"Fixing syntax in {file_path}")
        fix_syntax_issues(file_path)
    
    print("Done!")

if __name__ == '__main__':
    main()