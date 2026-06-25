import re

def main():
    filepath = r"E:\Apps\Java Projects\DocuTrack\src\app\Main.java"
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()

    # We want to replace these blocks:
    # System.out.println("\n=================================");
    # System.out.println("      DOCUTRACK LOGIN");
    # System.out.println("=================================");
    # with printHeader("DOCUTRACK LOGIN");
    
    # Regex to match the menu headers:
    header_pattern = re.compile(
        r'System\.out\.println\("\\n=+="\);\s*'
        r'System\.out\.println\("([^"]+)"\);\s*'
        r'System\.out\.println\("=+"\);',
        re.MULTILINE
    )
    
    def repl_header(m):
        title = m.group(1).strip()
        return f'printHeader("{title}");'
        
    content = header_pattern.sub(repl_header, content)
    
    # Same for short headers like:
    # System.out.println("\n========== CO1 ==========");
    short_header_pattern = re.compile(
        r'System\.out\.println\("\\n=+ ([^=]+) =+"\);',
        re.MULTILINE
    )
    
    def repl_short(m):
        title = m.group(1).strip()
        return f'printHeader("{title}");'
        
    content = short_header_pattern.sub(repl_short, content)

    # For options:
    # System.out.println("1. Something");
    # We can replace the dot with space for a cleaner look or enclose in brackets.
    # We will use "[1] Something"
    option_pattern = re.compile(r'System\.out\.println\("(\d+)\.\s*([^"]+)"\);')
    content = option_pattern.sub(r'System.out.println("  [\1] \2");', content)
    
    # Let's add the printHeader method before the last closing brace of the file.
    header_method = """
    public static void printHeader(String title) {
        System.out.println("\\n");
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ ██████╗  ██████╗  ██████╗██╗   ██╗████████╗██████╗  █████╗  ██████╗██╗  ██╗           ║");
        System.out.println("║ ██╔══██╗██╔═══██╗██╔════╝██║   ██║╚══██╔══╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝           ║");
        System.out.println("║ ██║  ██║██║   ██║██║     ██║   ██║   ██║   ██████╔╝███████║██║     █████╔╝            ║");
        System.out.println("║ ██║  ██║██║   ██║██║     ██║   ██║   ██║   ██╔══██╗██╔══██║██║     ██╔═██╗            ║");
        System.out.println("║ ██████╔╝╚██████╔╝╚██████╗╚██████╔╝   ██║   ██║  ██║██║  ██║╚██████╗██║  ██╗           ║");
        System.out.println("║ ╚═════╝  ╚═════╝  ╚═════╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝           ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════════╣");
        int totalWidth = 87;
        int padding = (totalWidth - title.length()) / 2;
        int paddingRight = totalWidth - title.length() - padding;
        String format = "║%" + (padding == 0 ? "" : padding) + "s%s%" + (paddingRight == 0 ? "" : paddingRight) + "s║\\n";
        System.out.printf(format, "", title, "");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════╝");
    }
}"""
    
    # Replace the last closing brace
    content = content.rstrip()
    if content.endswith("}"):
        content = content[:-1] + header_method
        
    with open(filepath, "w", encoding="utf-8") as f:
        f.write(content)

if __name__ == "__main__":
    main()
