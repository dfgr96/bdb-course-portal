export interface Module {
  id: number;
  title: string;
  contentType: string;
  contentUrl: string;
  orderIndex: number;
  completed?: boolean;
  progressPercent?: number;
}