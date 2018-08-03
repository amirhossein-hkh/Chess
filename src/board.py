from player import Player
from chesspiece import Color, Pawn, Bishop, Rook, King, Knight, Queen
from copy import deepcopy

class Board:
    """Board class represent a chessboard

    the board has 64 squares arranged in an 8×8 grid and chess pieces are laid over it.

    Attributes:
        grid (list of list of ChessPiece): an 8x8 list of list for holding the chess pieces
        player_white (Player): the player that play with the white pieces
        player_black (Player): the player that play with the black pieces

    """
    def __init__(self,player_white, player_black):
        self.grid = [[None for _ in range(8)] for _ in range(8)]
        self.player_white = player_white
        self.player_black = player_black
        self._setup()

    def _setup(self):
        """setting up the board and arranging the pieces"""
        for i in range(8):
            # initialising pawns
            self.grid[1][i] = Pawn(1, i, Color.BLACK, self)
            self.grid[6][i] = Pawn(6, i, Color.WHITE, self)

        for i in [2, 5]:
            # initialising bishops
            self.grid[0][i] = Bishop(0, i, Color.BLACK, self)
            self.grid[7][i] = Bishop(7, i, Color.WHITE, self)

        for i in [0, 7]:
            # initialising rooks
            self.grid[0][i] = Rook(0, i, Color.BLACK, self)
            self.grid[7][i] = Rook(7, i, Color.WHITE, self)

        for i in [1, 6]:
            # initialising knights
            self.grid[0][i] = Knight(0, i, Color.BLACK, self)
            self.grid[7][i] = Knight(7, i, Color.WHITE, self)

        # initialising queens
        self.grid[0][3] = Queen(0, 3, Color.BLACK, self)
        self.grid[7][3] = Queen(7, 3, Color.WHITE, self)

        # initialising kings
        self.grid[0][4] = King(0, 4, Color.BLACK, self)
        self.grid[7][4] = King(7, 4, Color.WHITE, self)

    def move(self,start_row, start_col, end_row, end_col, color):
        """get a move from player and check whether it is possible or not if so do it"""
        if self.grid[start_row][start_col] is None or self.grid[start_row][start_col].color != color:
            return False

        board_copy = deepcopy(self)
        chess_piece = self.grid[start_row][start_col]
        successful_move = chess_piece.move_to(end_row, end_col)
        if successful_move:
            opponent = self.player_white if color == Color.BLACK else self.player_black
            king_row , king_col = self.find_king(opponent.color)

            if not self.is_safe(king_row, king_col, opponent.color):
                opponent.check = True

            if isinstance(self.grid[start_row][start_col], King) and abs(end_col-start_col) == 2:
                # castling
                rook_row = 7 if color == Color.WHITE else 0
                if end_col > start_col:
                    self.grid[rook_row][end_col-1] = self.grid[rook_row][7]
                    self.grid[rook_row][7] = None
                else:
                    self.grid[rook_row][end_col + 1] = self.grid[rook_row][0]
                    self.grid[rook_row][0] = None

            if abs(start_row - end_row) == 1 and abs(start_col-end_col) == 1 and \
                    self.grid[end_row][end_col] is None and isinstance(self.grid[start_row][start_col], Pawn):
                # capturing in an En passant move
                self.grid[start_row][end_col] = None

            if isinstance(self.grid[start_row][start_col], Pawn) and \
                    end_row == (0 if self.grid[start_row][start_col].color == Color.WHITE else 7):
                # Promotion
                promoted_piece = eval(input('do u want to promote to which piece : (Queen, Rook, Bishop, Knight').title())
                self.grid[end_row][end_col] = promoted_piece(end_row, end_col, self.grid[start_row][start_col].color, self)

            self.grid[end_row][end_col] = self.grid[start_row][start_col]
            self.grid[start_row][start_col] = None

            self_player = self.player_white if color == Color.WHITE else self.player_black
            self_king = self.find_king(self_player.color)
            if self_player.check:
                if self.is_safe(self_king.row, self_king.col, self_player.color):
                    self_player.check = False
                else:
                    self.grid = board_copy.grid
                    self.player_black = board_copy.player_black
                    self.player_white = board_copy.player_white
                    return False

            return True
        else:
            return False

    def is_safe(self, row, col, color):
        opponent_color = Color.WHITE if color == Color.BLACK else Color.BLACK
        opponent_covered_squares = set()

        for i in range(8):
            for j in range(8):
                if self.grid[i][j] is not None and self.grid[i][j].color == opponent_color and\
                        not isinstance(self.grid[i][j], King):
                    possible_moves = self.grid[i][j].generate_possible_moves()
                    for element in possible_moves:
                        opponent_covered_squares.add(element)

        return (row, col) not in opponent_covered_squares

    def is_any_move_left(self, color):
        for i in range(8):
            for j in range(8):
                if self.grid[i][j] is not None and self.grid[i][j].color == color:
                    possible_moves = self.grid[i][j].generate_possible_moves()
                    for row_move, col_move in possible_moves:
                        copied_board = deepcopy(self)
                        if copied_board.move(i, j, row_move, col_move, color):
                            return True
        return False

    def find_king(self, color):
        for i in range(8):
            for j in range(8):
                if self.grid[i][j] is not None and isinstance(self.grid[i][j], King) and self.grid[i][j].color == color:
                    return i, j

    def print_board(self):
        print('    {}'.format(' '.join([str(x) for x in range(8)])))
        for i in range(8):
            print('   {}'.format('-' * (8 * 2 + 1)))
            print(' {}'.format(i), end=' ')
            for j in range(8):
                print('| ' if self.grid[i][j] is None
                      else '|{0.char}'.format(self.grid[i][j]), end='')
            print('|')
        print('   {}'.format('-' * (8 * 2 + 1)))
