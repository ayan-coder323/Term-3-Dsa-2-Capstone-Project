import urllib.request
import urllib.parse
import json

dot_code = """digraph G {
    node [shape=box, style="filled,rounded", color="#38B2AC", fillcolor="#0F1B2D", fontcolor="#ffffff", fontname="Segoe UI"];
    edge [color="#A0AEC0", fontcolor="#ffffff", fontname="Segoe UI", fontsize=10];
    bgcolor="#162A45";
    rankdir=TD;

    Start [label="Start DocuTrack", shape=oval];
    Login [label="Authentication\\nLogin Screen", shape=diamond, fillcolor="#162A45", color="#38B2AC"];
    Start -> Login;

    AdminRole [label="Admin Role", color="#EDD88B", fillcolor="#1A365D"];
    UserRole [label="User Role", color="#EDD88B", fillcolor="#1A365D"];

    Login -> AdminRole [label="admin"];
    Login -> UserRole [label="user"];

    AdminMenu [label="ADMIN DASHBOARD\\nFull Access", color="#68D391"];
    UserMenu [label="USER DASHBOARD\\nRead-Only", color="#68D391"];

    AdminRole -> AdminMenu;
    UserRole -> UserMenu;

    Search [label="1. Search Document"];
    View [label="2. View All Documents"];
    ULogout [label="3. Logout"];

    UserMenu -> Search;
    UserMenu -> View;
    UserMenu -> ULogout;
    ULogout -> Login [style=dashed, label="Return"];

    CO1 [label="CO1 - Indexing"];
    CO2 [label="CO2 - Analytics"];
    CO3 [label="CO3 - Network"];
    CO4 [label="CO4 - Path Optimization"];
    CO5 [label="CO5 - Sorting & Ranking"];
    CO6 [label="CO6 - Storage Opt"];
    ALogout [label="7. Logout"];

    AdminMenu -> CO1;
    AdminMenu -> CO2;
    AdminMenu -> CO3;
    AdminMenu -> CO4;
    AdminMenu -> CO5;
    AdminMenu -> CO6;
    AdminMenu -> ALogout;
    ALogout -> Login [style=dashed, label="Return"];

    Shared [label="Shared Document Pool\\nBST, AVL, Graph, Cost Matrix", shape=cylinder, color="#81E6D9", fillcolor="#1E3A5F"];

    CO1 -> Shared [style=dashed, label=" Updates/Syncs"];
    CO2 -> Shared [style=dashed, label=" Reads"];
    CO3 -> Shared [style=dashed, label=" Reads/Edits"];
    CO4 -> Shared [style=dashed, label=" Reads/Edits"];
    CO5 -> Shared [style=dashed, label=" Reads"];
    CO6 -> Shared [style=dashed, label=" Reads"];
    Search -> Shared [style=dashed, label=" Reads"];
    View -> Shared [style=dashed, label=" Reads"];
}"""

url = "https://quickchart.io/graphviz"
data = json.dumps({"graph": dot_code, "format": "png"}).encode('utf-8')
req = urllib.request.Request(url, data=data, headers={'Content-Type': 'application/json', 'User-Agent': 'Mozilla/5.0'})

try:
    with urllib.request.urlopen(req) as response:
        with open("cli_menu_flow.png", "wb") as f:
            f.write(response.read())
    print("Successfully generated cli_menu_flow.png via QuickChart!")
except Exception as e:
    print(f"Error: {e}")
