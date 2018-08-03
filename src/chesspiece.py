from enum import Enum
from abc import abstractmethod, ABC


class Color(Enum):

    """Color is an enum for color of chess pieces"""

    WHITE = 1
    BLACK = 2


class ChessPiece(ABC):

    """This is an abstract class representing a chess piece

    Attributes:
        row (int): an int indicating the row in which the piece started.
        col (int): an int indicating the column in which the piece started.
        color (Color): an enum object indicating the color of the piece
        board (Board): a board object over which the chess pieces are laid.

    """
    def __init__(self, row, col, color, board):
        self.row = row
        self.col = col
        self.color = color
        self.board = board

    @abstractmethod
    def generate_possible_moves(self):
        """generate all the possible moves from the current position"""
        ...

    def move_to(self, row, col):
        """make a move to the given position (row, col)"""
        possible_moves = self.generate_possible_moves()
        if (row, col) in possible_moves:
            self.row = row
            self.col = col
            return True
        else:
            return False


class Pawn(ChessPiece):

    """Pawn class represent a pawn in chess

    The pawn (♙,♟) is the most numerous piece in the game of chess, and in most circumstances, also the weakest.
    It historically represents infantry. Each player begins a game with eight pawns, one on each square of
    the rank immediately in front of the other pieces.

    The pawn can move forward to the unoccupied square immediately in front of it on the same file,or on its first move
    it can advance two squares along the same file, provided both squares are unoccupied(black dots in the diagram);
    or the pawn can capture an opponent's piece on a square diagonally in front of it on an adjacent file, by moving to
    that square (black "x"s). A pawn has two special moves: the en passant capture and promotion.

    Attributes:
        moved (bool): A boolean indicating whether the pawn ever moved or not
        char (str): a character representing the pawn shape
        two_square_advance (bool): A boolean representing whether the pawn has advanced two square or not
    """

    def __init__(self, row, col, color, board):
        self.moved = False
        self.two_square_advance = False
        self.char = '♟' if color == Color.WHITE else '♙'
        super().__init__(row, col, color, board)

    def generate_possible_moves(self):
        move_dir = -1 if self.color == Color.WHITE else 1
        possible_moves = []

        if self.board.grid[self.row + move_dir][self.col] is None:
            # check advancing the forward move
            possible_moves.append((self.row + move_dir, self.col))

        if not self.moved and self.board.grid[self.row + 2 * move_dir][self.col] is None:
            # check whether is the first time that pawn moves
            possible_moves.append((self.row + 2 * move_dir, self.col))

        for i in [1, -1]:
            try:
                if self.board.grid[self.row + move_dir][self.col + i] is not None and \
                        self.board.grid[self.row + move_dir][self.col + i].color != self.color:
                    # check Capturing
                    possible_moves.append((self.row + move_dir, self.col + i))

                if self.row == (3 if self.color == Color.WHITE else 5) and \
                        self.board.grid[self.row][self.col + i] is not None and \
                        isinstance(self.board.grid[self.row][self.col + i], Pawn) and \
                        self.board.grid[self.row][self.col + i].two_square_advance:
                    # En passant
                    possible_moves.append((self.row + move_dir, self.col + i))
            except IndexError:
                pass

        return possible_moves

    def move_to(self, row, col):
        two_square_forward = 4 if self.color == Color.WHITE else 3
        if super().move_to(row, col):
            if not self.moved and row == two_square_forward:
                self.two_square_advance = True
            else:
                self.two_square_advance = False
            self.moved = True
            return True
        else:
            return False

    def __repr__(self):
        return "{} Pawn in ({},{})".format(self.color.name, self.row, self.col)


class Bishop(ChessPiece):

    """This class will represent a bishop in the chess game

    A bishop (♗,♝) is a piece in the board game of chess. Each player begins the game with two bishops.One starts
    between the king's knight and the king, the other between the queen's knight and the queen.

    The starting squares are c1 and f1 for White's bishops, and c8 and f8 for Black's bishops.The bishop can move any
    number of squares diagonally, but cannot leap over other pieces.

    Attributes:
        char (str): a character representing the pawn shape
    """
    def __init__(self, row, col, color, board):
        self.char = '♝' if color == Color.WHITE else '♗'
        super().__init__(row, col, color, board)

    def generate_possible_moves(self):
        possible_moves = []

        for row_step, col_step in [(1, 1), (1, -1), (-1, 1), (-1, -1)]:
            row_move = row_step + self.row
            col_move = col_step + self.col
            while 0 <= row_move < 8 and 0 <= col_move < 8:
                if self.board.grid[row_move][col_move] is None:
                    possible_moves.append((row_move, col_move))
                else:
                    if self.board.grid[row_move][col_move].color != self.color:
                        # Capturing
                        possible_moves.append(([row_move, col_move]))
                    break
                row_move += row_step
                col_move += col_step

        return possible_moves


class Queen(ChessPiece):

    """This class will represent a queen piece in chess game

    The queen (♕,♛) is the most powerful piece in the game of chess, able to move any number of squares vertically,
    horizontally or diagonally. Each player starts the game with one queen, placed in the middle of the first rank next
    to the king. Because the queen is the strongest piece, a pawn is promoted to a queen in the vast majority of cases.

    The queen combines the power of a rook and bishop and can move any number of squares along a rank, file,or diagonal,
    but cannot leap over other pieces.

    Attributes:
        char (str) : a character representing a queen
    """

    def __init__(self, row, col, color, board):
        self.char = '♛' if color == Color.WHITE else '♕'
        super().__init__(row, col, color, board)

    def generate_possible_moves(self):
        possible_moves = []

        for row_step, col_step in [(1, 1), (1, -1), (-1, 1), (-1, -1)]:
            row_move = row_step + self.row
            col_move = col_step + self.col
            while 0 <= row_move < 8 and 0 <= col_move < 8:
                if self.board.grid[row_move][col_move] is None:
                    possible_moves.append((row_move, col_move))
                else:
                    if self.board.grid[row_move][col_move].color != self.color:
                        # Capturing
                        possible_moves.append(([row_move, col_move]))
                    break
                row_move += row_step
                col_move += col_step

        for row_step, col_step in [(1, 0), (-1, 0), (0, 1), (0, -1)]:
            row_move = row_step + self.row
            col_move = col_step + self.col
            while 0 <= row_move < 8 and 0 <= col_move < 8:
                if self.board.grid[row_move][col_move] is None:
                    possible_moves.append((row_move, col_move))
                else:
                    if self.board.grid[row_move][col_move].color != self.color:
                        # Capturing
                        possible_moves.append(([row_move, col_move]))
                    break
                row_move += row_step
                col_move += col_step

        return possible_moves


class Rook(ChessPiece):
    """This class represent a rook in chess game

    A rook (♖,♜) is a piece in the strategy board game of chess. Formerly the piece was called the tower,
    marquess, rector, and comes (Sunnucks 1970). The term castle is considered informal, incorrect, or old-fashioned.
    The rook can move any number of squares along a rank or file, but cannot leap over other pieces.
    Along with the king, a rook is involved during the king's castling move.

    The rook can move any number of squares along a rank or file, but cannot leap over other pieces.Along with the
    king, a rook is involved during the king's castling move.

    Attributes:
        char (str): a character representing the rook shape
        moved (str): a boolean represent whether the rook has moved or not
    """
    def __init__(self, row, col, color, board):
        self.char = '♜' if color == Color.WHITE else '♖'
        self.moved = False
        super().__init__(row, col, color, board)

    def generate_possible_moves(self):
        possible_moves = []

        for row_step, col_step in [(1, 0), (-1, 0), (0, 1), (0, -1)]:
            row_move = row_step + self.row
            col_move = col_step + self.col
            while 0 <= row_move < 8 and 0 <= col_move < 8:
                if self.board.grid[row_move][col_move] is None:
                    possible_moves.append((row_move, col_move))
                else:
                    if self.board.grid[row_move][col_move].color != self.color:
                        # Capturing
                        possible_moves.append(([row_move, col_move]))
                    break
                row_move += row_step
                col_move += col_step

        return possible_moves

    def move_to(self, row, col):
        if super().move_to(row, col):
            self.moved = True
            return True
        else:
            return False


class Knight(ChessPiece):

    """This class will represent a knight piece in chess game

    The knight (♘ ♞) is a piece in the game of chess, representing a knight (armored cavalry). It is normally
    represented by a horse's head and neck. Each player starts with two knights, which begin on the row closest
    to the player, between the rooks and bishops.

    The knight moves to any of the closest squares that are not on the same rank, file, or diagonal, thus the move
    forms an "L"-shape: two squares vertically and one square horizontally, or two squares horizontally and one square
    vertically. The knight is the only piece that can leap over other pieces.

    Attributes:
        char (str): a character representing the knight shape
    """
    def __init__(self, row, col, color, board):
        self.char = '♞' if color == Color.WHITE else '♘'
        super().__init__(row, col, color, board)

    def generate_possible_moves(self):
        possible_moves = []

        for row_step, col_step in [(1, 2), (-1, 2), (1, -2), (-1, -2), (2, 1), (2, -1), (-2, 1), (-2, -1)]:
            row_move = self.row + row_step
            col_move = self.col + col_step
            if 0 <= row_move < 8 and 0 <= col_move < 8:
                if self.board.grid[row_move][col_move] is None:
                    possible_moves.append((row_move, col_move))
                elif self.board.grid[row_move][col_move].color != self.color:
                    # Capturing
                    possible_moves.append(([row_move, col_move]))

        return possible_moves


class King(ChessPiece):

    """This class will represent the king piece in chess game

    In chess, the king (♔,♚) is the most important piece. The object of the game is to threaten the opponent's king in
    such a way that escape is not possible (checkmate). If a player's king is threatened with capture, it is said to be
    in check, and the player must remove the threat of capture on the next move. If this cannot be done, the king is
    said to be in checkmate, resulting in a loss for that player. Although the king is the most important piece, it is
    usually the weakest piece in the game until a later phase, the endgame. Players cannot make any move that places
    their own king in check.

    The king moves one square in any direction. The king also has a special move called castling that involves also
    moving a rook.

    Attributes:
        char (str): a character representing the king shape
        moved (str): a boolean represent whether the rook has moved or not
    """

    def __init__(self, row, col, color, board):
        self.char = '♚' if color == Color.WHITE else '♔'
        self.moved = False
        super().__init__(row, col, color, board)

    def generate_possible_moves(self):
        possible_moves = []

        for row_step in [-1, 0, 1]:
            for col_step in [-1, 0, 1]:
                if row_step != 0 and col_step != 0:
                    row_move = self.row + row_step
                    col_move = self.col + col_step
                    if self.board.is_safe(row_move, col_move, self.color):
                        if self.board.grid[row_move][col_move] is None or \
                                self.board.grid[row_move][col_move].color != self.color:
                            possible_moves.append((row_move, col_move))

        for i in [0, 7]:
            # castling
            piece = self.board.grid[i][0 if self.color == Color.WHITE else 7]
            if not self.moved and isinstance(piece, Rook) and not piece.moved:
                # Neither the king nor the chosen rook has previously moved.
                move_dir = 1 if i == 7 else -1
                for j in range(0, 3):
                    # the hole path is safe
                    if self.board.is_safe(self.row, self.col + j * move_dir):
                        break
                else:
                    start = 1 if i == 0 else 6
                    for k in range(min(self.row + 1, start), max(self.row, start + 1)):
                        # There are no pieces between the king and the chosen rook.
                        if self.board.grid[i][k] is not None:
                            break
                    else:
                        possible_moves.append((self.row, self.col + 2 * move_dir))

    def move_to(self, row, col):
        if super().move_to(row, col):
            self.moved = True
            return True
        else:
            return False
