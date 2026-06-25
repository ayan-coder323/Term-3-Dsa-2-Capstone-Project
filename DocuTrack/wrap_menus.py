import re

def main():
    filepath = r"E:\Apps\Java Projects\DocuTrack\src\app\Main.java"
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()

    # Find sequences of printMenuOption("..."); and wrap them in printMenuTop() and printMenuBottom()
    
    # We can match multiple consecutive printMenuOption lines
    # Pattern: a sequence of lines containing printMenuOption
    # It might be easier to iterate line by line
    
    lines = content.split('\n')
    new_lines = []
    in_menu = False
    
    for i, line in enumerate(lines):
        if 'printMenuOption(' in line:
            if not in_menu:
                # Started a menu block, insert printMenuTop() before this line
                indent = line[:len(line) - len(line.lstrip())]
                new_lines.append(indent + 'printMenuTop();')
                in_menu = True
            new_lines.append(line)
        else:
            if in_menu:
                # Just finished a menu block, insert printMenuBottom()
                # But wait, what if the next line is just empty or a comment? Let's assume the block of options is contiguous.
                # Let's use the indent of the previous line
                prev_indent = lines[i-1][:len(lines[i-1]) - len(lines[i-1].lstrip())]
                new_lines.append(prev_indent + 'printMenuBottom();')
                in_menu = False
            new_lines.append(line)
            
    # Handle case if file ends while in_menu
    if in_menu:
        new_lines.append('        printMenuBottom();')
        
    content = '\n'.join(new_lines)
    
    # Update printMenuOption definition to match the new box style, and add printMenuTop/Bottom
    
    old_option_def = re.compile(r'public static void printMenuOption\(String text\)\s*\{\s*System\.out\.println\(optionColor \+ text \+ ANSI_RESET\);\s*\}', re.MULTILINE)
    
    new_option_def = """public static void printMenuTop() {
        System.out.println();
        System.out.println(borderColor + "    ╭─────────────────────────────────────────────────────────────────────────────╮" + ANSI_RESET);
    }

    public static void printMenuOption(String text) {
        int totalInnerWidth = 77;
        // Text contains ANSI codes? No, text is raw string like "  [1] Something"
        int textLen = text.length();
        int paddingRight = totalInnerWidth - textLen - 2; // -2 for left padding inside table
        if(paddingRight < 0) paddingRight = 0;
        
        System.out.print(borderColor + "    │  " + ANSI_RESET);
        System.out.print(optionColor + text + ANSI_RESET);
        for(int i=0; i<paddingRight; i++) System.out.print(" ");
        System.out.println(borderColor + "│" + ANSI_RESET);
    }

    public static void printMenuBottom() {
        System.out.println(borderColor + "    ╰─────────────────────────────────────────────────────────────────────────────╯" + ANSI_RESET);
        System.out.println();
    }"""
    
    content = old_option_def.sub(new_option_def, content)

    with open(filepath, "w", encoding="utf-8") as f:
        f.write(content)

if __name__ == "__main__":
    main()
