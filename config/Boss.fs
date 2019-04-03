(* Make mills to remove your opponent's pieces! *)
open System


(* game loop *)
while true do
    (* myColor: your color, BLACK (first player) or WHITE (second player) *)
    (* turnsLeft: the number of turns before game over (starts at 200) *)
    (* myPieces: the number of your pieces on the board, which is also your score *)
    (* opponentsPieces: the number of your opponent's pieces on the board (his/her score) *)
    (* myStock: the number of pieces you still have to put on the board (0 after the placement phase) *)
    (* opponentsStock: the number of pieces your opponent still has to put on the board *)
    let token = (Console.In.ReadLine()).Split [|' '|]
    let myColor = token.[0]
    let turnsLeft = int(token.[1])
    let myPieces = int(token.[2])
    let opponentsPieces = int(token.[3])
    let myStock = int(token.[4])
    let opponentsStock = int(token.[5])
    let numPieces = int(Console.In.ReadLine()) (* the total number of pieces on the board *)
    for i in 0 .. numPieces - 1 do
        (* pieceId: the unique ID of the piece (white and black pieces do not share IDs) *)
        (* ownerId: 0 for of piece of mines, 1 for a piece of the opponent *)
        (* pieceDirection: 0 to 7, the radial direction where the piece is. 0 top left, 1 top, and so on clockwise. *)
        (* pieceRadius: 0 to 2, the radius from the center of the location of the piece. 0 innermost, 1 middle, 2 outermost. *)
        let token1 = (Console.In.ReadLine()).Split [|' '|]
        let pieceId = int(token1.[0])
        let ownerId = int(token1.[1])
        let pieceDirection = int(token1.[2])
        let pieceRadius = int(token1.[3])
        ()

    let numOptions = int(Console.In.ReadLine()) (* the total number of suggested moves. At least 1. *)
    let options = [
        for i in 0 .. numOptions - 1 do
            let commandText = Console.In.ReadLine() (* a valid output line. All possible outputs are listed, but the jumps. The jumps are listed only if no other move is possible. *)
            yield commandText
    ]

    
    (* Write an action using printfn *)
    (* To debug: eprintfn "Debug message" *)
    

    (* PUT <direction> <radius> <remove1> <remove2> | MOVE <pieceId> <direction> <radius> <remove1> <remove2> *)
    printfn "%s" (List.head options)
    ()