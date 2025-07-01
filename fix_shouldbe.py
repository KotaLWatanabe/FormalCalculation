#!/usr/bin/env python3
import re
import sys
import os

def fix_shouldbe_in_file(file_path):
    """Fix shouldBe infix usage to method call syntax"""
    with open(file_path, 'r') as f:
        content = f.read()
    
    # Pattern to match expressions like "expression shouldBe value"
    # This handles both simple and complex expressions
    pattern = r'(\w+|\([^)]+\)|[^.]\w+(?:\.[^.\s]+)*)\s+shouldBe\s+(.+?)(?=\n|$|//)'
    
    def replace_shouldbe(match):
        expr = match.group(1).strip()
        value = match.group(2).strip()
        return f'{expr}.shouldBe({value})'
    
    # Apply the transformation
    new_content = re.sub(pattern, replace_shouldbe, content, flags=re.MULTILINE)
    
    # Handle any remaining cases manually
    new_content = re.sub(r'\)\s+shouldBe\s+', ').shouldBe(', new_content)
    new_content = re.sub(r'(\w+)\s+shouldBe\s+', r'\1.shouldBe(', new_content)
    
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
        print(f"Fixing {file_path}")
        fix_shouldbe_in_file(file_path)
    
    print("Done!")

if __name__ == '__main__':
    main()