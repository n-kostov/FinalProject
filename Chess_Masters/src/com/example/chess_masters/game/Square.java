package com.example.chess_masters.game;

import com.example.chess_masters.pieces.Piece;

public class Square
{
  private boolean attacked;
  private Piece piece;
  private boolean selected;

  public Square(Piece paramPiece)
  {
    setPiece(paramPiece);
    this.selected = false;
    this.attacked = false;
  }

  public Piece getPiece()
  {
    return this.piece;
  }

  public boolean isAttacked()
  {
    return this.attacked;
  }

  public boolean isSelected()
  {
    return this.selected;
  }

  public void setAttacked(boolean paramBoolean)
  {
    this.attacked = paramBoolean;
  }

  public void setPiece(Piece paramPiece)
  {
    this.piece = paramPiece;
  }

  public void setSelected(boolean paramBoolean)
  {
    this.selected = paramBoolean;
  }
}

/* Location:           C:\Users\spind\Desktop\ChessMasters_dex2jar.jar
 * Qualified Name:     com.example.chess_masters.game.Square
 * JD-Core Version:    0.6.0
 */